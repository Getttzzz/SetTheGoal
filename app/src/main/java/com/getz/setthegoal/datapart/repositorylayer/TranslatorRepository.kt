package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.datapart.datasource.ITranslatorDS
import com.getz.setthegoal.domainpart.repositorylayer.ITranslatorRepository

class TranslatorRepository(
    private val translatorDS: ITranslatorDS
) : BaseRepository(), ITranslatorRepository {

    override suspend fun translateRusToEng(inputRusText: String): String =
        translatorDS.getTranslatedInEngTextSync(inputRusText)
}