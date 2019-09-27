package com.getz.setthegoal.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val firestoreModule = Kodein.Module(ModulesNames.FIRESTORE_MODULE) {
    bind<FirebaseFirestore>() with singleton { FirebaseFirestore.getInstance() }
    bind<FirebaseAuth>() with singleton { FirebaseAuth.getInstance() }
}