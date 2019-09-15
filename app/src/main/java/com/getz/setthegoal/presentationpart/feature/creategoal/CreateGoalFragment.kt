package com.getz.setthegoal.presentationpart.feature.creategoal

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.children
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.addOnTextChangedListener
import com.getz.setthegoal.presentationpart.util.gone
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.visible
import kotlinx.android.synthetic.main.fragment_create_goal.*
import kotlin.random.Random

class CreateGoalFragment : BaseFragment(R.layout.fragment_create_goal) {

    companion object {
        const val IS_FAMILY_ARGS = "IS_FAMILY_ARGS"

        fun getInstance(isForFamily: Boolean) = CreateGoalFragment()
            .apply { arguments = bundleOf(IS_FAMILY_ARGS to isForFamily) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isForFamily = arguments?.getBoolean(IS_FAMILY_ARGS, false)

        etGoal.addOnTextChangedListener { inputText ->
            val possibleWords = inputText.trim().split(" ")
            if (possibleWords.size >= 2) mbUpdateSuggestions.visible() else mbUpdateSuggestions.gone()
        }

        mbUpdateSuggestions.setSingleClickListener {
            //todo utilize a request to semantic api
            //todo hide Chips and show Pictures. Hide this btn, show save but when user selects pic.
        }


        //todo slowly scrolling adapter with huge amount of goals. It will be so damn cool!!!!!!
        //Adapter with chips. Scroll horizontal.
//        chipSuggestionsGroup.children.forEachIndexed { index, view ->
//
//        }
    }

}