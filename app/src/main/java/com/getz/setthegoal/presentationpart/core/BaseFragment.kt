package com.getz.setthegoal.presentationpart.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

interface KodeinComponentInitializer<T> : KodeinAware {
    object PARENT_FRAGMENT_TAG

    val parentKodein: Kodein

    @Suppress("UNCHECKED_CAST")
    val kodeinComponent: T
        get() = this as T

    val kodeinModuleName: String get() = this::class.java.simpleName

    fun provideOverridingModule(): Kodein.Module = Kodein.Module(kodeinModuleName) {}

    fun initializeKodein(): Kodein
}

interface KodeinFragmentInitializer : KodeinComponentInitializer<Fragment> {
    val ignoreParentFragment: Boolean get() = false

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun initializeKodein() = Kodein.lazy {
        val parentFragment = kodeinComponent.parentFragment

        if (parentFragment is KodeinAware) {
//            println("GETTTZZZ.KodeinFragmentInitializer.initializeKodein ---> parent fragment is KodeinAware, so extend parentFragment.kodein=${parentFragment.kodein} and override")
            extend(parentFragment.kodein, allowOverride = true)
        } else {
//            println("GETTTZZZ.KodeinFragmentInitializer.initializeKodein ---> parent fragment isn't KodeinAware, extend parentKodein=$parentKodein and override")
            extend(parentKodein, allowOverride = true)
        }

        if (parentFragment != null && !ignoreParentFragment) {
//            println("GETTTZZZ.KodeinFragmentInitializer.initializeKodein ---> I am child fragment")
            bind<Fragment>(tag = KodeinComponentInitializer.PARENT_FRAGMENT_TAG) with provider { parentFragment!! }
            bind<Fragment>(overrides = true) with provider { kodeinComponent }
        } else {
//            println("GETTTZZZ.KodeinFragmentInitializer.initializeKodein ---> I am a parent")
            bind<Fragment>() with provider { kodeinComponent }
        }
//        println("GETTTZZZ.KodeinFragmentInitializer.initializeKodein ---> parentFragment=$parentFragment")
//        println("GETTTZZZ.KodeinFragmentInitializer.initializeKodein ---> kodeinComponent=$kodeinComponent")
//        println("GETTTZZZ.KodeinFragmentInitializer.initializeKodein ---> import module with name=${provideOverridingModule().name}")
        import(provideOverridingModule(), allowOverride = true)
    }
}

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId),
    KodeinFragmentInitializer {
    override val parentKodein: Kodein by kodein()

    @Suppress("LeakingThis")
    override val kodein = initializeKodein()
}