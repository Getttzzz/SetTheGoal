package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.core.IRepository
import com.getz.setthegoal.domainpart.entitylayer.Word

interface IPartOfSpeechRepository : IRepository {
    suspend fun getWords(inputText: String, onResult: suspend (List<Word>) -> Unit)
}