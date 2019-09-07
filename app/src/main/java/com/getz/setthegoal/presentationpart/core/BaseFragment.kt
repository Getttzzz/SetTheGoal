package com.getz.setthegoal.presentationpart.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId), KodeinAware {

    override val kodein by kodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("GETZ.BaseFragment.onCreate ---> ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("GETZ.BaseFragment.onViewCreated ---> ")
    }

}