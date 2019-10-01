package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteGoalUC
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository

class DeleteGoalUC(
    private val goalRepo: IGoalRepository
) : BaseUseCase(), IDeleteGoalUC {
    override suspend fun invoke(
        request: String,
        onError: (Throwable) -> Unit,
        onResult: suspend (Boolean) -> Unit
    ) = withDefault(onError) {
        goalRepo.deleteGoal(request) {}
    }
}