package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.getz.setthegoal.presentationpart.feature.creategoal.applydeadline.ApplyDeadlineFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.applyfinish.ApplyFinishFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.applyforwhom.ApplyWhoFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.applypicture.ApplyPictureFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks.ApplySubGoalFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.applytext.ApplyTextFragment

const val STEPS_TO_CREATE = 6

class CreateGoalPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val APPLY_WHO_TAB_POSITION = 0
        const val APPLY_TEXT_TAB_POSITION = 1
        const val APPLY_PICTURE_TAB_POSITION = 2
        const val APPLY_SUBTASKS_TAB_POSITION = 3
        const val APPLY_DEADLINE_TAB_POSITION = 4
        const val APPLY_FINISH_TAB_POSITION = 5
    }

    override fun getItem(position: Int): Fragment = when (position) {
        APPLY_WHO_TAB_POSITION -> ApplyWhoFragment()
        APPLY_TEXT_TAB_POSITION -> ApplyTextFragment()
        APPLY_PICTURE_TAB_POSITION -> ApplyPictureFragment()
        APPLY_SUBTASKS_TAB_POSITION -> ApplySubGoalFragment()
        APPLY_DEADLINE_TAB_POSITION -> ApplyDeadlineFragment()
        APPLY_FINISH_TAB_POSITION -> ApplyFinishFragment()
        else -> throw IllegalArgumentException("Wrong position.")
    }

    override fun getCount() = STEPS_TO_CREATE
}