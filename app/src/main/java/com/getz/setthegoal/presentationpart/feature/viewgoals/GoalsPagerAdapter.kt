package com.getz.setthegoal.presentationpart.feature.viewgoals

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class GoalsPagerAdapter(fm: FragmentManager, private val titles: ArrayList<String>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val FAMILY_TAB_POSITION = 0
        const val MYSELF_TAB_POSITION = 1
        const val ACHIEVED_POSITION = 2
    }

    override fun getItem(position: Int): Fragment = when (position) {
        FAMILY_TAB_POSITION -> GoalsForFamilyFragment()
        MYSELF_TAB_POSITION -> GoalsForMyselfFragment()
        ACHIEVED_POSITION -> AchievedFragment()
        else -> throw IllegalArgumentException("Wrong position.")
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int) = titles[position]
}