package com.getz.setthegoal.presentationpart.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId), KodeinAware {
    override val kodein by kodein()
}