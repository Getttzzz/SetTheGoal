package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.RequestPOS
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS

interface IPartOfSpeechDataSource {
    suspend fun getPartOfSpeechAsync(
        requestPOS: RequestPOS,
        onResult: suspend (ResponsePOS) -> Unit
    )
}