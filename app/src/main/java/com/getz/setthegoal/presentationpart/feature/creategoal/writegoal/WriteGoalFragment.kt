package com.getz.setthegoal.presentationpart.feature.creategoal.writegoal

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.util.addOnTextChangedListener
import kotlinx.android.synthetic.main.fragment_write_goal.*
import org.kodein.di.direct
import org.kodein.di.generic.instance

class WriteGoalFragment : BaseFragment(R.layout.fragment_write_goal) {
    lateinit var vm: CreateGoalVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNextBtnValidation()
        setupAdapter()
    }

    private fun setupNextBtnValidation() {
        etGoal.addOnTextChangedListener { inputText ->
            val possibleWords = inputText.trim().split(" ")
            val enabled = possibleWords.size >= 2
            vm.nextButtonSharedLD.value = enabled
        }
    }

    private fun setupAdapter() {
        val suggestions = GoalSuggestions.getSuggestions()

        suggestions.shuffle()

        rvSuggestions.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        rvSuggestions.adapter = SuggestionsRV(suggestions) { position ->
            val selectedStrResInt = suggestions[position]
            val selectedText = getString(selectedStrResInt)
            etGoal.setText(getString(R.string.i_want_to, selectedText))
        }
    }
}