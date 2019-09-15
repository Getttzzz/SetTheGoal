package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.BuildConfig
import com.getz.setthegoal.datapart.api.TexterraApi
import com.getz.setthegoal.datapart.entitylayer.RequestPOS
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS

class PartOfSpeechRemoteDS(
    val api: TexterraApi
) : IPartOfSpeechDS {

    override suspend fun getPartOfSpeechAsync(
        requestPOS: RequestPOS,
        onResult: suspend (ResponsePOS) -> Unit
    ) {
        val partOfSpeechFromText = api.getPartOfSpeechFromText(
            targetType = "pos-token",
            apikey = BuildConfig.TexterraApiKey,
            requestPOS = requestPOS
        )

        onResult(partOfSpeechFromText)
    }
}