package com.getz.setthegoal.presentationpart.feature.creategoal

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.util.addKeyboardListener
import com.getz.setthegoal.presentationpart.util.addOnPageSelectedListener
import com.getz.setthegoal.presentationpart.util.removeKeyboardListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.swipeLeft
import com.getz.setthegoal.presentationpart.util.swipeRight
import kotlinx.android.synthetic.main.fragment_create_goal.*
import org.kodein.di.direct
import org.kodein.di.generic.instance

class CreateGoalFragment : BaseFragment(R.layout.fragment_create_goal) {

    lateinit var vm: CreateGoalVM
    lateinit var bridge: CreateGoalBridge
    private var keyboardListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    companion object {
        const val IS_FAMILY_ARGS = "IS_FAMILY_ARGS"

        fun getInstance(isForFamily: Boolean) = CreateGoalFragment()
            .apply { arguments = bundleOf(IS_FAMILY_ARGS to isForFamily) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as CreateGoalBridge
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("GETTTZZZ.CreateGoalFragment.onViewCreated ---> ")

        vm.isForFamily = arguments?.getBoolean(IS_FAMILY_ARGS, false)!!
        setupViewPager()
        setupLD()
    }

    override fun onResume() {
        super.onResume()
        //clRootCreateGoal.doOnLayout {}
        keyboardListener = clRootCreateGoal.addKeyboardListener { isOpened ->
            vm.keyboardListenerLD.value = isOpened

            if (btnNext != null) {
                btnNext.isVisible = !isOpened
            }
        }
    }

    override fun onPause() {
        clRootCreateGoal.removeKeyboardListener(keyboardListener)
        keyboardListener = null
        super.onPause()
    }

    private fun setupLD() {
        vm.nextButtonSharedLD.observe(this, Observer { enabled ->
            btnNext.isEnabled = enabled
        })
        vm.pressNextSharedLD.observe(this, Observer { btnNext.performClick() })
    }

    private fun setupViewPager() {
        selectPage(CreateGoalPagerAdapter.WRITE_GOAL_TAB_POSITION)
        vpCreateGoal.adapter = CreateGoalPagerAdapter(childFragmentManager)
        vpCreateGoal.addOnPageSelectedListener { position -> selectPage(position) }
        vpCreateGoal.offscreenPageLimit = STEPS_TO_CREATE
        tlCreateGoal.setupWithViewPager(vpCreateGoal, true)
    }

    private fun selectPage(position: Int) {
        when (position) {
            CreateGoalPagerAdapter.WRITE_GOAL_TAB_POSITION -> {
                btnNext.text = getString(R.string.next)
                btnPrevious.text = getString(R.string.close)

                btnNext.setSingleClickListener {
                    vm.recognizePartsOfSpeech()
                    vpCreateGoal.swipeRight(position)
                }
                btnPrevious.setSingleClickListener { bridge.closeCreateFragment() }
            }
            CreateGoalPagerAdapter.APPLY_PICTURE_TAB_POSITION -> {
                btnNext.text = getString(R.string.next)
                btnPrevious.text = getString(R.string.previous)

                btnNext.setSingleClickListener { vpCreateGoal.swipeRight(position) }
                btnPrevious.setSingleClickListener { vpCreateGoal.swipeLeft(position) }
            }
            CreateGoalPagerAdapter.APPLY_SUBTASKS_TAB_POSITION -> {
                btnNext.text = getString(R.string.next)
                btnPrevious.text = getString(R.string.previous)

                btnNext.setSingleClickListener { vpCreateGoal.swipeRight(position) }
                btnPrevious.setSingleClickListener { vpCreateGoal.swipeLeft(position) }
            }
            CreateGoalPagerAdapter.APPLY_DEADLINE_TAB_POSITION -> {
                btnNext.text = getString(R.string.save)
                btnPrevious.text = getString(R.string.previous)

                btnNext.setSingleClickListener {
                    vm.saveGoal()
                    vpCreateGoal.swipeRight(position)
                }
                btnPrevious.setSingleClickListener { vpCreateGoal.swipeLeft(position) }
            }
            CreateGoalPagerAdapter.APPLY_FINISH_TAB_POSITION -> {
                btnNext.text = getString(R.string.finish)
                btnPrevious.text = getString(R.string.close)

                btnNext.setSingleClickListener { bridge.closeCreateFragment() }
                btnPrevious.setSingleClickListener { bridge.closeCreateFragment() }
            }
        }
    }


}