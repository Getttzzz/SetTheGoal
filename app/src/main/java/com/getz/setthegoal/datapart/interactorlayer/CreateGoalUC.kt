package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.ICreateGoalUC
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository
import com.getz.setthegoal.domainpart.repositorylayer.IPhotoRepository

class CreateGoalUC(
    private val goalRepo: IGoalRepository,
    private val photoRepo: IPhotoRepository
) : BaseUseCase(), ICreateGoalUC {

    override suspend fun invoke(
        writableEntity: Goal,
        onError: (Throwable) -> Unit,
        onResult: suspend (Boolean) -> Unit
    ): Any? = withDefault(onError) {

        goalRepo.createGoal(writableEntity) { success ->

            sendIncrementDownloadRequest(writableEntity)

            onResult(success)
        }

    }

    /**
     *  Send request to increment counter of photo downloads for Unsplash
     *  if user selects photo while creating a goal.
     * */
    private suspend fun sendIncrementDownloadRequest(writableEntity: Goal) {
        writableEntity.photo?.incrementDownloadLink?.let { link ->
            photoRepo.markAsDownloadedForUnsplash(link)
        }
    }
}