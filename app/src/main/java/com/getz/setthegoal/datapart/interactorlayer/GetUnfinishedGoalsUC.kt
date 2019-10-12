package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.IGetUnfinishedGoalsUC
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository

class GetUnfinishedGoalsUC(
    private val repository: IGoalRepository
) : BaseUseCase(), IGetUnfinishedGoalsUC {
    override suspend fun invoke(
        request: Unit,
        onError: (Throwable) -> Unit,
        onResult: suspend (List<Goal>) -> Unit
    ) = withDefault(onError) {
        repository.getUnfinishedGoals { onResult(it) }
    }
}