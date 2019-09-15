package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.domainpart.repositorylayer.IPartOfSpeechRepository

class GetPartsOfSpeechUC(
    private val repository: IPartOfSpeechRepository
) : BaseUseCase(), IGetPartsOfSpeechUC {

    override suspend fun invoke(
        request: String,
        onError: (Throwable) -> Unit,
        onResult: suspend (List<Word>) -> Unit
    ) = withDefault(onError) {

        repository.getWords(request) { words ->
            withUI(onError) { onResult(words) }
        }
    }
}