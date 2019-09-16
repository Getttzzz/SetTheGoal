package com.getz.setthegoal.presentationpart.feature.creategoal

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.addOnPageSelectedListener
import com.getz.setthegoal.presentationpart.util.gone
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.swipeLeft
import com.getz.setthegoal.presentationpart.util.swipeRight
import com.getz.setthegoal.presentationpart.util.visible
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
        setupViewPager()

//        etGoal.addOnTextChangedListener { inputText ->
//            val possibleWords = inputText.trim().split(" ")
//            btnNext.isEnabled = possibleWords.size >= 2
//        }
//        mbUpdateSuggestions.setSingleClickListener {}
        //todo slowly scrolling adapter with huge amount of goals. It will be so damn cool!!!!!!
        //todo Adapter with chips. Scroll horizontal.
    }

    private fun setupViewPager() {
        selectPage(CreateGoalPagerAdapter.WRITE_GOAL_TAB_POSITION)
        vpCreateGoal.adapter = CreateGoalPagerAdapter(childFragmentManager)
        vpCreateGoal.addOnPageSelectedListener { position -> selectPage(position) }
        tlCreateGoal.setupWithViewPager(vpCreateGoal, true)
    }

    private fun selectPage(position: Int) {
        btnNext.setSingleClickListener { vpCreateGoal.swipeRight(position) }
        btnPrevious.setSingleClickListener { vpCreateGoal.swipeLeft(position) }
        when (position) {
            CreateGoalPagerAdapter.WRITE_GOAL_TAB_POSITION -> {
                btnPrevious.gone()
                btnNext.text = getString(R.string.next)
            }
            CreateGoalPagerAdapter.APPLY_PICTURE_TAB_POSITION -> {
                btnPrevious.visible()
                btnNext.text = getString(R.string.next)
            }
            CreateGoalPagerAdapter.APPLY_SUBTASKS_TAB_POSITION -> {
                btnPrevious.visible()
                btnNext.text = getString(R.string.next)
            }
            CreateGoalPagerAdapter.APPLY_DEADLINE_TAB_POSITION -> {
                btnPrevious.visible()
                btnNext.text = getString(R.string.finish)
            }
        }
    }


}