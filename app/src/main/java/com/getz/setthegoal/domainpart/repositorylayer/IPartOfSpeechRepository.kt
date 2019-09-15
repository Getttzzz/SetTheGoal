package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.core.IRepository

interface IPartOfSpeechRepository : IRepository {
    suspend fun getListPOS(
        //todo some input data here

    )
}