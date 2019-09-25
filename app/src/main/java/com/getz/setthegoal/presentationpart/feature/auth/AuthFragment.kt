package com.getz.setthegoal.presentationpart.feature.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.gone
import com.getz.setthegoal.presentationpart.util.say
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.visible
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var bridge: AuthBridge

    companion object {
        const val SIGN_IN_REQUEST_CODE = 345
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as AuthBridge
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity!!, gso)


        btnGoogle.setSingleClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE)
        }

        btnIncognito.setSingleClickListener {
            firebaseAuth.signInAnonymously()
                .addOnSuccessListener {
                    bridge.openMainScreenAfterAuth()
                }
                .addOnFailureListener {
                    this.say(getString(R.string.auth_as_incognito_is_failed))
                }
        }

        val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(activity!!)

        if (lastSignedInAccount != null) {
            tvGoogleAccountInfo.visible()
            btnGoogleSignOut.visible()

            tvGoogleAccountInfo.text =
                getString(R.string.looks_like_you_were_here_under_google, lastSignedInAccount.email)

            btnGoogleSignOut.setSingleClickListener {
                googleSignInClient.signOut().addOnSuccessListener {
                    tvGoogleAccountInfo.gone()
                    btnGoogleSignOut.gone()
                    this.say(getString(R.string.signed_out_successfully))
                }.addOnFailureListener {
                    this.say(getString(R.string.signed_out_error))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SIGN_IN_REQUEST_CODE -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    handleSignInResult(task)
                }
            }
        } else {
            //cancelled
            println("GETTTZZZ.AuthFragment.onActivityResult ---> cancelled")
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            if (account != null) {
                authWithFirebase(account)
            } else {
                this.say(getString(R.string.acc_is_null))
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            this.say(getString(R.string.attempt_to_get_acc_failed, e.statusCode))
        }
    }

    private fun authWithFirebase(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    println("GETTTZZZ.AuthFragment.authWithFirebase ---> got user! user=${firebaseAuth.currentUser}")
                    bridge.openMainScreenAfterAuth()
                } else {
                    this.say(getString(R.string.auth_wasnt_successful))
                }
            }.addOnFailureListener {
                this.say(getString(R.string.auth_failed))
            }
    }
}