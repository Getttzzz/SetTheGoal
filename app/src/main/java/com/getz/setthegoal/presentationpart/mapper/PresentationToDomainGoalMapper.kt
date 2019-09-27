package com.getz.setthegoal.presentationpart.mapper

import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI

class PresentationToDomainGoalMapper : Gandalf<GoalUI, Goal> {

    override fun transform(source: GoalUI): Goal {
        return Goal(source.text)
    }
}