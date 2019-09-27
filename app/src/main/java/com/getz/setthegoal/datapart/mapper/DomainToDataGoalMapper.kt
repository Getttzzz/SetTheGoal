package com.getz.setthegoal.datapart.mapper

import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.google.firebase.auth.FirebaseAuth

class DomainToDataGoalMapper(
    private val auth: FirebaseAuth
) : Gandalf<Goal, GoalDto> {

    override fun transform(source: Goal): GoalDto {
        val uid = auth.currentUser?.uid ?: "empty_uid"
        return GoalDto(source.text, uid)
    }

}