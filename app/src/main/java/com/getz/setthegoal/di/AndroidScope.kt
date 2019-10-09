package com.getz.setthegoal.di

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.internal.synchronizedIfNull

object AndroidScope : Scope<Any> {
    private val newRegistry = ::StandardScopeRegistry
    private val map = HashMap<LifecycleOwner, ScopeRegistry>()

    override fun getRegistry(context: Any): ScopeRegistry {
        (context as? LifecycleOwner)
            ?: throw IllegalStateException("The context of an AndroidScope must be a LifecycleOwner")

        return synchronizedIfNull(
            lock = map,
            predicate = { map[context] },
            ifNotNull = { it },
            ifNull = {
                val registry = newRegistry()
                map[context] = registry
                // note: to use DefaultLifecycleObserver it requires
                // implementation "androidx.lifecycle:lifecycle-common-java8:2.1.0
                context.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        println("GETTTZZZ.AndroidScope.onDestroy ---> map.remove(context) context=${context}")
                        registry.clear()
                        map.remove(context)
                        context.lifecycle.removeObserver(this)
                    }
                })
                registry
            }
        )
    }
}