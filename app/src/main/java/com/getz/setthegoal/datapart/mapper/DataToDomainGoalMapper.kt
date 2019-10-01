package com.getz.setthegoal.datapart.mapper

import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal

class DataToDomainGoalMapper : Gandalf<List<GoalDto>, List<Goal>> {
    override fun transform(source: List<GoalDto>): List<Goal> {
        val result = arrayListOf<Goal>()

        source.forEach { dto ->

            result.add(
                Goal(
                    goalId = dto.goalId,
                    text = dto.text,
                    photo = dto.photo,
                    subGoals = dto.subGoals,
                    deadline = dto.deadline,
                    forWhom = dto.forWhom,
                    done = dto.done
                )
            )
        }

        return result
    }
}