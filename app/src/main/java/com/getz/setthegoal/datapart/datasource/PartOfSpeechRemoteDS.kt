package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.BuildConfig
import com.getz.setthegoal.datapart.api.TexterraApi
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.entitylayer.TextObj

class PartOfSpeechRemoteDS(
    val api: TexterraApi
) : IPartOfSpeechDS {

    override suspend fun getPartOfSpeechAsync(
        requestPOS: List<TextObj>,
        onResult: suspend (ResponsePOS) -> Unit
    ) {
        val partOfSpeechFromText = api.getPartOfSpeechFromText(
            targetType = "pos-token",
            apikey = BuildConfig.TexterraApiKey,
            requestPOS = requestPOS
        )

        if (partOfSpeechFromText.isNotEmpty()) {
            onResult(partOfSpeechFromText[0])
        } else {
            throw Throwable("Result was empty.")
        }
    }
}