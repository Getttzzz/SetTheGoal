package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.api.TexterraApi
import com.getz.setthegoal.datapart.entitylayer.RequestPOS
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS

class PartOfSpeechRemoteDataSource(
    val api: TexterraApi
) : IPartOfSpeechDataSource {

    override suspend fun getPartOfSpeechAsync(
        requestPOS: RequestPOS,
        onResult: suspend (ResponsePOS) -> Unit
    ) {
        api.getPartOfSpeechFromText(
            targetType = "pos-token",
            apikey = "", //todo think how to get api key from res...
            requestPOS = requestPOS
        )

    }
}