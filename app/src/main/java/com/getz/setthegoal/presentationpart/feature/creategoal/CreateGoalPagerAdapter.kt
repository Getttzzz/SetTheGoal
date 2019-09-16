package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.getz.setthegoal.presentationpart.feature.creategoal.applydeadline.ApplyDeadlineFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.applypicture.ApplyPictureFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks.ApplySubtasksFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.writegoal.WriteGoalFragment

class CreateGoalPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val WRITE_GOAL_TAB_POSITION = 0
        const val APPLY_PICTURE_TAB_POSITION = 1
        const val APPLY_SUBTASKS_TAB_POSITION = 2
        const val APPLY_DEADLINE_TAB_POSITION = 3
    }

    override fun getItem(position: Int): Fragment = when (position) {
        WRITE_GOAL_TAB_POSITION -> WriteGoalFragment()
        APPLY_PICTURE_TAB_POSITION -> ApplyPictureFragment()
        APPLY_SUBTASKS_TAB_POSITION -> ApplySubtasksFragment()
        APPLY_DEADLINE_TAB_POSITION -> ApplyDeadlineFragment()
        else -> throw IllegalArgumentException("Wrong position.")
    }

    override fun getCount() = 4
}