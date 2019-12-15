package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteAllGoalsUC
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository

class DeleteAllGoalsUC(
    private val goalRepo: IGoalRepository
) : BaseUseCase(), IDeleteAllGoalsUC {
    override suspend fun invoke(
        request: Unit,
        onError: (Throwable) -> Unit,
        onResult: suspend (Boolean) -> Unit
    ) = withDefault(onError) {
        goalRepo.deleteAllGoals {
            onResult(it)
        }
    }

}