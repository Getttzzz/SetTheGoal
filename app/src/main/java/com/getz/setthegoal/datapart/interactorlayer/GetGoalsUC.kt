package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.IGetGoalsUC
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository

class GetGoalsUC(
    private val repo: IGoalRepository
) : BaseUseCase(), IGetGoalsUC {

    override fun invoke(
        request: Unit,
        onError: (Throwable) -> Unit,
        onResult: (List<Goal>) -> Unit
    ) {
        try {
            repo.getGoals { onResult(it) }
        } catch (e: Throwable) {
            onError(e)
        }
    }
}