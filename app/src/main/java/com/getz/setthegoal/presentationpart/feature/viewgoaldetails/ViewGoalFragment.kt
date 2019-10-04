package com.getz.setthegoal.presentationpart.feature.viewgoaldetails

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.util.getHideableListener
import kotlinx.android.synthetic.main.fragment_view_goal.*

class ViewGoalFragment : BaseFragment(R.layout.fragment_view_goal) {

    private lateinit var goal: GoalUI
    private val subGoalAdapter: ViewSubGoalAdapter by lazy { setupAdapter() }

    companion object {
        private const val PARCELABLE_GOAL = "PARCELABLE_GOAL"
        fun getInstance(goalUI: GoalUI) = ViewGoalFragment()
            .apply { arguments = Bundle().apply { this.putParcelable(PARCELABLE_GOAL, goalUI) } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { goal = it.getParcelable(PARCELABLE_GOAL) as GoalUI }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlideApp.with(this)
            .load(goal.photo?.urls?.regular)
            .apply(RequestOptions().centerCrop())
            .listener(getHideableListener(pbPhotoView))
            .error(R.drawable.layer_list_bee)
            .into(ivPhotoView)

        tvGoalView.text = goal.text

        subGoalAdapter.replace(goal.subGoals)
    }

    private fun setupAdapter() = ViewSubGoalAdapter().apply {
        rvSubGoal.setHasFixedSize(true)
        rvSubGoal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvSubGoal.adapter = this
    }
}