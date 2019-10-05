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

            val photoUI = if (goal.photo != null) {
                val urlsDomain = goal.photo.urls
                val urls = UrlsUI(
                    urlsDomain.raw,
                    urlsDomain.full,
                    urlsDomain.regular,
                    urlsDomain.small,
                    urlsDomain.thumb
                )

                PhotoUI(
                    urls = urls,
                    incrementDownloadLink = goal.photo.incrementDownloadLink,
                    userName = goal.photo.userName,
                    profileLink = goal.photo.profileLink,
                    isSelected = false
                )
            } else null

            val subGoals = arrayListOf<SubGoalUI>()
            goal.subGoals.forEach { subGoalDomain ->
                subGoals.add(
                    SubGoalUI(
                        goal = subGoalDomain.goal,
                        order = subGoalDomain.order,
                        done = subGoalDomain.done
                    )
                )
            }

            result.add(
                GoalUI(
                    goalId = goal.goalId,
                    text = goal.text,
                    photo = photoUI,
                    subGoals = subGoals,
                    deadline = goal.deadline,
                    forWhom = goal.forWhom,
                    done = goal.done,
                    createdAt = goal.createdAt,
                    updatedAt = goal.updatedAt
                )
            )
        }

        return result
    }

}