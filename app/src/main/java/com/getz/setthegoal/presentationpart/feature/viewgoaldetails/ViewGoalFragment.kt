package com.getz.setthegoal.presentationpart.feature.viewgoaldetails

import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.entitylayer.DeadlineEnum
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.util.getDaysIn
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

        setupPhoto()
        setupGoalText()
        setupSubGoals()
        setupDeadline()
    }

    private fun setupDeadline() {
        if (goal.deadline.isNotEmpty()) {
            val timeRange = DeadlineEnum.getEnumByTimeRange(goal.deadline)
            tvDeadlineView.text = getString(timeRange.strRes)

            val daysRemain = goal.createdAt.getDaysIn(timeRange)

            tvTimeRemain.text = buildSpannedString {
                append(resources.getQuantityText(R.plurals.you_have_plural, daysRemain))
                append(" ")
                bold {
                    append(
                        resources.getQuantityString(R.plurals.days_plural, daysRemain, daysRemain)
                    )
                }
                append(" ")
                append(getString(R.string.to_get_it_done))
            }
        }
    }

    private fun setupSubGoals() {
        clSubGoalsContainerView.isVisible = goal.subGoals.isNotEmpty()
        vSeparator2.isVisible = goal.subGoals.isNotEmpty()
        subGoalAdapter.replace(goal.subGoals)
    }

    private fun setupGoalText() {
        etGoal.setText(goal.text)
    }

    private fun setupPhoto() {
        val photo = goal.photo?.urls?.regular ?: ""

        if (photo.isNotEmpty()) {
            GlideApp.with(this)
                .load(photo)
                .apply(RequestOptions().centerCrop())
                .listener(getHideableListener(pbPhotoView))
                .error(R.drawable.layer_list_bee)
                .into(ivPhotoView)
        } else {
            ivPhotoView.isVisible = false
            pbPhotoView.isVisible = false
            vSeparator1.isVisible = false
        }
    }

    private fun setupAdapter() = ViewSubGoalAdapter().apply {
        rvSubGoal.setHasFixedSize(true)
        rvSubGoal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvSubGoal.adapter = this
    }
}