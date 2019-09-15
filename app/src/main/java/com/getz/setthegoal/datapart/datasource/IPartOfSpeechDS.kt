package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.entitylayer.TextObj

interface IPartOfSpeechDS {
    suspend fun getPartOfSpeechAsync(
        requestPOS: List<TextObj>,
        onResult: suspend (ResponsePOS) -> Unit
    )
}