package com.getz.setthegoal.presentationpart.feature.creategoal.writegoal

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.datapart.entitylayer.GoalSuggestions
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.util.addTextListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_write_goal.*
import org.kodein.di.direct
import org.kodein.di.generic.instance

class WriteGoalFragment : BaseFragment(R.layout.fragment_write_goal) {
    private lateinit var vm: CreateGoalVM

    private val smoothScrollHandler = Handler(Looper.getMainLooper())
    private lateinit var runnable: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLD()
        setupNextBtnValidation()
        setupAdapter()
        btnNextSecondary.setSingleClickListener { vm.pressNextSharedLD.value = Unit }
    }

    override fun onResume() {
        super.onResume()

        startAutoScrolling()
    }

    override fun onPause() {
        super.onPause()

        rvSuggestions.stopScroll()
    }

    override fun onStop() {
        super.onStop()

        smoothScrollHandler.removeCallbacks(runnable)
    }

    private fun setupLD() {
        vm.keyboardListenerLD.observe(this, Observer { isOpened ->
            if (btnNextSecondary == null) return@Observer
            btnNextSecondary.post { btnNextSecondary.isVisible = isOpened }
        })
    }

    private fun setupNextBtnValidation() {
        etGoal.addTextListener { inputText ->
            val possibleWords = inputText.trim().split(" ")
            val enabled = possibleWords.size >= 2
            vm.nextButtonSharedLD.value = enabled
            vm.writtenGoalText = inputText
            btnNextSecondary.isEnabled = enabled
            rvSuggestions.stopScroll()
        }
    }

    private fun setupAdapter() {
        val suggestions = GoalSuggestions.getSuggestions()

        suggestions.shuffle()

        val suggestionAdapter = SuggestionAdapter()
            .apply {
                onClick = { position ->
                    val selectedStrResInt = this.godList[position]
                    val selectedText = getString(selectedStrResInt)
                    etGoal.setText(getString(R.string.i_want_to, selectedText))
                }
            }
        rvSuggestions.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        rvSuggestions.adapter = suggestionAdapter

        suggestionAdapter.replace(suggestions)
    }

    private fun startAutoScrolling() {
        runnable = { rvSuggestions.smoothScrollBy(6000, 0, LinearInterpolator(), 90_000) }
        smoothScrollHandler.postDelayed(runnable, 400)
    }
}