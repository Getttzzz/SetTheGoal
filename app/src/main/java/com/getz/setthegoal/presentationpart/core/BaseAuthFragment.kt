package com.getz.setthegoal.presentationpart.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.util.say
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

abstract class BaseAuthFragment(@LayoutRes layoutId: Int) : BaseFragment(layoutId) {

    protected open val onSignedInSuccessfully: () -> Unit = {}
    protected open val onLastSignedInUserDetected: (String) -> Unit = {}

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val client: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(requireActivity(), gso)
    }

    companion object {
        const val SIGN_IN_PROFILE_REQUEST_CODE = 346
        const val SIGN_IN_PROFILE_WHEN_INCOGNITO_REQUEST_CODE = 347
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        lastSignedInAccount?.email?.let { onLastSignedInUserDetected(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    SIGN_IN_PROFILE_REQUEST_CODE -> {
                        handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data), false)
                    }
                    SIGN_IN_PROFILE_WHEN_INCOGNITO_REQUEST_CODE -> {
                        handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data), true)
                    }
                }
            }
            /**
             * If result is cancelled for release build, it needs to find SHA-1 fingerprint
             * for release keystore and add that fingerprint to Firebase Console.
             * After that step, generate new google-service.json file and insert into the project.
             * */
            Activity.RESULT_CANCELED -> say(getString(R.string.google_sign_in_error))
            else -> say(getString(R.string.something_went_wrong))
        }
    }

    fun requestGoogleSignIn() {
        val signInIntent = client.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_PROFILE_REQUEST_CODE)
    }

    fun requestGoogleSignInWhenIncognito() {
        val signInIntent = client.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_PROFILE_WHEN_INCOGNITO_REQUEST_CODE)
    }

    fun makeSignInIncognito() {
        firebaseAuth.signInAnonymously()
            .addOnSuccessListener { onSignedInSuccessfully() }
            .addOnFailureListener { this.say(getString(R.string.auth_as_incognito_is_failed)) }
    }

    fun makeSignOutFromGoogle(onSuccess: () -> Unit) {
        client.signOut()
            .addOnSuccessListener {
                onSuccess()
                this.say(getString(R.string.signed_out_successfully))
            }.addOnFailureListener {
                this.say(getString(R.string.signed_out_error))
            }
    }

    fun getUser() = firebaseAuth.currentUser

    fun makeSignOutFromFirebase() {
        firebaseAuth.signOut()
    }

    private fun handleSignInResult(
        completedTask: Task<GoogleSignInAccount>,
        wasIncognito: Boolean
    ) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                if (wasIncognito) {
                    authWithFirebaseAfterIncognitoMode(account)
                } else {
                    authWithFirebase(account)
                }
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
                    onSignedInSuccessfully()
                } else {
                    this.say(getString(R.string.auth_wasnt_successful))
                }
            }.addOnFailureListener {
                this.say(getString(R.string.auth_failed))
            }
    }

    private fun authWithFirebaseAfterIncognitoMode(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.currentUser?.linkWithCredential(credential)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    onSignedInSuccessfully()
                } else {
                    this.say(getString(R.string.linking_account_was_failed))
                }
            }?.addOnFailureListener {
                this.say(getString(R.string.linking_account_was_failed))
            }
    }
}