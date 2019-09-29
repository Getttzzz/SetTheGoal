package com.getz.setthegoal.presentationpart.mapper

import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.entitylayer.PhotoUI
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.entitylayer.UrlsUI

class DomainToPresentationGoalMapper : Gandalf<List<Goal>, List<GoalUI>> {

    override fun transform(source: List<Goal>): List<GoalUI> {
        val result = arrayListOf<GoalUI>()

        source.forEach { goal ->

            val urls = UrlsUI("", "", "", "", "")
            val photoUI = PhotoUI(urls, "", "", "", false)
            val subGoals = arrayListOf(SubGoalUI("", 1))
            val deadline = "six_months"
            val forWhom = "family"

            result.add(GoalUI(goal.text, photoUI, subGoals, deadline, forWhom))
        }

        return result
    }

}