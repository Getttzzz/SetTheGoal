package com.getz.setthegoal.presentationpart.feature.creategoal.applytext

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.datapart.entitylayer.GoalSuggestions
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.util.addTextListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_apply_text_goal.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.on

class ApplyTextFragment : BaseFragment(R.layout.fragment_apply_text_goal) {
    val vm: CreateGoalVM by kodein.on(context = this).instance()

    private val suggestionAdapter: SuggestionAdapter by lazy { setupSuggestionAdapter() }
    private val smoothScrollHandler = Handler(Looper.getMainLooper())
    private val smoothScrollRunnable: Runnable by lazy {
        Runnable {
            rvSuggestions.smoothScrollBy(
                6000,
                0,
                LinearInterpolator(),
                90_000
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLD()
        setupNextBtnValidation()
        fillSuggestions()
        btnNextSecondary.setSingleClickListener { vm.pressNextSharedLD.value = Unit }
    }

    override fun onResume() {
        super.onResume()
        vm.validateText()
        startAutoScrolling()
    }

    override fun onPause() {
        super.onPause()
        rvSuggestions.stopScroll()
    }

    override fun onStop() {
        super.onStop()
        smoothScrollHandler.removeCallbacks(smoothScrollRunnable)
    }

    private fun setupLD() {
        vm.keyboardListenerLD.observe(this, Observer { isOpened ->
            if (btnNextSecondary != null) {
                btnNextSecondary.isVisible = isOpened
            }
        })
    }

    private fun setupNextBtnValidation() {
        etGoal.addTextListener { inputText ->
            vm.writtenGoalText = inputText
            val isValid = vm.validateText()
            btnNextSecondary.isEnabled = isValid
            rvSuggestions.stopScroll()
        }
    }

    private fun setupSuggestionAdapter() = SuggestionAdapter().apply {
        onClick = { position ->
            val selectedStrResInt = this.godList[position]
            val selectedText = getString(selectedStrResInt)
            etGoal.setText(getString(R.string.i_want_to, selectedText))
        }
        rvSuggestions.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        rvSuggestions.adapter = this
    }

    private fun fillSuggestions() {
        val suggestions = GoalSuggestions.getSuggestions()
        suggestions.shuffle()
        suggestionAdapter.replace(suggestions)
    }

    private fun startAutoScrolling() {
        smoothScrollHandler.postDelayed(smoothScrollRunnable, 400)
    }
}