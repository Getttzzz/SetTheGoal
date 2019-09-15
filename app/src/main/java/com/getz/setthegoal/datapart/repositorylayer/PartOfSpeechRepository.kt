package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.datapart.datasource.IPartOfSpeechDS
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.entitylayer.TextObj
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.repositorylayer.IPartOfSpeechRepository

class PartOfSpeechRepository(
    private val remotePartOfSpeechDS: IPartOfSpeechDS,
    private val gandalf: Gandalf<ResponsePOS, List<Word>>
) : BaseRepository(), IPartOfSpeechRepository {

    override suspend fun getWords(inputText: String, onResult: suspend (List<Word>) -> Unit) {
        val request = listOf(TextObj(inputText))

        remotePartOfSpeechDS.getPartOfSpeechAsync(request) { response ->
            onResult(gandalf.transform(response))
        }
    }
}