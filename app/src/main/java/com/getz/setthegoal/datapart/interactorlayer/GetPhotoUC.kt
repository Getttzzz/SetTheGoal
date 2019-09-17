package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.api.QuoteApiLanguageEnum
import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.interactorlayer.IGetPhotoUC
import com.getz.setthegoal.domainpart.repositorylayer.IPhotoRepository
import com.getz.setthegoal.domainpart.repositorylayer.ITranslatorRepository
import java.util.Locale

class GetPhotoUC(
    private val repositoryPhoto: IPhotoRepository,
    private val repositoryTranslator: ITranslatorRepository
) : BaseUseCase(), IGetPhotoUC {
    override suspend fun invoke(
        request: Pair<String, Locale>,
        onError: (Throwable) -> Unit,
        onResult: suspend (List<Photo>) -> Unit
    ): Any? = withDefault(onError) {

        var queryText = request.first

        if (request.second == QuoteApiLanguageEnum.RU.locale) {
            queryText = repositoryTranslator.translateRusToEng(queryText)
        }

        repositoryPhoto.getPhotos(queryText) { photos ->
            withUI(onError) {
                onResult(photos)
            }
        }
    }
}