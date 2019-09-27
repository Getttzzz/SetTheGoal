package com.getz.setthegoal.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.singleton

val firestoreModule = Kodein.Module(ModulesNames.FIRESTORE_MODULE) {

    /**
     * eager singleton will be created as soon as the Kodein instance is created
     * */
    bind<FirebaseFirestore>() with eagerSingleton {
        FirebaseFirestore.getInstance().apply {
            var settings = this.firestoreSettings
            if (!settings.isPersistenceEnabled) {
                settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
            }
            this.firestoreSettings = settings
        }
    }

    bind<FirebaseAuth>() with singleton { FirebaseAuth.getInstance() }
}

