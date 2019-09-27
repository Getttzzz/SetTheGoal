package com.getz.setthegoal.di

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance

const val GET_CONNECTION_STATUS_ACTION = "GET_CONNECTION_STATUS_ACTION"
const val EXTRA_ONLINE_STATUS = "EXTRA_ONLINE_STATUS"

val connectionModule = Kodein.Module(ModulesNames.CONNECTION_MODULE) {
    bind<ConnectivityManager>() with eagerSingleton {
        val cm = ContextCompat.getSystemService(instance(), ConnectivityManager::class.java)
                as ConnectivityManager

        val firestoreNetListener = FirestoreNetListener(instance(), instance())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(firestoreNetListener)
        } else {
            cm.registerNetworkCallback(NetworkRequest.Builder().build(), firestoreNetListener)
        }

        cm
    }
}

private class FirestoreNetListener(
    private val firestore: FirebaseFirestore,
    private val context: Context
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        firestore.enableNetwork().addOnSuccessListener { }
        sendInternetStatus(context, true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        firestore.disableNetwork().addOnSuccessListener { }
        sendInternetStatus(context, false)
    }
}

private fun sendInternetStatus(context: Context, isOnline: Boolean) {
    if (isOnline) {
        println("GETTTZZZ.sendInternetStatus ---> Firestore is online")
    } else {
        println("GETTTZZZ.sendInternetStatus ---> Firestore is offline")
    }

    val intent = Intent(GET_CONNECTION_STATUS_ACTION)
        .apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(EXTRA_ONLINE_STATUS, isOnline)
        }
    context.sendBroadcast(intent)
}