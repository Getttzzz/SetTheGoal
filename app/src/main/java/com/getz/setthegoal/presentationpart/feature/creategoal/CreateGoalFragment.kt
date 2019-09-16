package com.getz.setthegoal.presentationpart.feature.creategoal

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.addOnPageSelectedListener
import com.getz.setthegoal.presentationpart.util.gone
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.swipeLeft
import com.getz.setthegoal.presentationpart.util.swipeRight
import com.getz.setthegoal.presentationpart.util.visible
import kotlinx.android.synthetic.main.fragment_create_goal.*
import org.kodein.di.direct
import org.kodein.di.generic.instance

class CreateGoalFragment : BaseFragment(R.layout.fragment_create_goal) {

    lateinit var vm: CreateGoalVM

    companion object {
        const val IS_FAMILY_ARGS = "IS_FAMILY_ARGS"

        fun getInstance(isForFamily: Boolean) = CreateGoalFragment()
            .apply { arguments = bundleOf(IS_FAMILY_ARGS to isForFamily) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
        println("GETTTZZZ.CreateGoalFragment.onCreate ---> vm=${vm.hashCode()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isForFamily = arguments?.getBoolean(IS_FAMILY_ARGS, false)
        setupViewPager()
        vm.nextButtonSharedLD.observe(this, Observer { enabled ->
            btnNext.isEnabled = enabled
        })
    }

    override fun onDestroy() {
        vm.nextButtonSharedLD.removeObservers(this)
        super.onDestroy()
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