package com.getz.setthegoal.presentationpart.feature.creategoal

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_create_goal.*

class CreateGoalFragment : BaseFragment(R.layout.fragment_create_goal) {

    companion object {
        const val IS_FAMILY_ARGS = "IS_FAMILY_ARGS"

        fun getInstance(isForFamily: Boolean) = CreateGoalFragment()
            .apply { arguments = bundleOf(IS_FAMILY_ARGS to isForFamily) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isForFamily = arguments?.getBoolean(IS_FAMILY_ARGS, false)
        println("GETTTZZZ.CreateGoalFragment.onViewCreated ---> isForFamily=$isForFamily")

        mbUpdateSuggestions.setSingleClickListener {
            //todo utilize a request to semantic api
        }
    }

}