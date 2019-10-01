package com.getz.setthegoal.presentationpart.mapper

import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.SubGoal
import com.getz.setthegoal.domainpart.entitylayer.UrlsDomain
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI

class PresentationToDomainGoalMapper : Gandalf<GoalUI, Goal> {

    override fun transform(source: GoalUI): Goal {


        val photoDomain = if (source.photo != null) {
            val urlsUI = source.photo.urls
            val urlsDomain = UrlsDomain(
                raw = urlsUI.raw,
                full = urlsUI.full,
                regular = urlsUI.regular,
                small = urlsUI.small,
                thumb = urlsUI.thumb
            )

            Photo(
                urls = urlsDomain,
                incrementDownloadLink = source.photo.incrementDownloadLink,
                userName = source.photo.userName,
                profileLink = source.photo.profileLink
            )
        } else null


        val subGoalsDomain = arrayListOf<SubGoal>()
        source.subGoals.forEach { subGoalUI ->
            subGoalsDomain.add(
                SubGoal(
                    goal = subGoalUI.goal,
                    order = subGoalUI.order,
                    done = subGoalUI.done
                )
            )
        }

        return Goal(
            text = source.text,
            photo = photoDomain,
            subGoals = subGoalsDomain,
            deadline = source.deadline,
            forWhom = source.forWhom,
            done = source.done
        )
    }
}