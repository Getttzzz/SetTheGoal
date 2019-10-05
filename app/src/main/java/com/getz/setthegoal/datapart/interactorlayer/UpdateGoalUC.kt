package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.IUpdateGoalUC
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository

class UpdateGoalUC(
    private val goalRepo: IGoalRepository
) : BaseUseCase(), IUpdateGoalUC {
    override suspend fun invoke(
        writableEntity: Goal,
        onError: (Throwable) -> Unit,
        onResult: suspend (Boolean) -> Unit
    ) = withDefault(onError) {
        goalRepo.updateGoal(writableEntity) { success ->
            onResult(success)
        }
    }
}