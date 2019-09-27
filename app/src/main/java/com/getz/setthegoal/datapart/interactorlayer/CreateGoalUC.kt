package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.ICreateGoalUC
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository

class CreateGoalUC(
    private val repository: IGoalRepository
) : BaseUseCase(), ICreateGoalUC {

    override suspend fun invoke(
        writableEntity: Goal,
        onError: (Throwable) -> Unit,
        onResult: suspend (Boolean) -> Unit
    ): Any? = withDefault(onError) {

        repository.createGoal(writableEntity) { success ->
            onResult(success)
        }

    }
}