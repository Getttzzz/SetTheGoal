package com.getz.setthegoal.ui.goals.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class GoalsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> GoalsForFamilyFragment()
        1 -> GoalsForMyselfFragment()
        else -> throw IllegalArgumentException("Wrong position.")
    }

    override fun getCount() = 2
}