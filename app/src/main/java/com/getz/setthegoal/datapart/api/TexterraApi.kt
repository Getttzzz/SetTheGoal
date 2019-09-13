package com.getz.setthegoal.datapart.api

import com.getz.setthegoal.datapart.entitylayer.RequestPOS
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TexterraApi {

    @POST("nlp")
    suspend fun getPartOfSpeechFromText(
        @Query("targetType") targetType: String,
        @Query("apikey") apikey: String,
        @Body requestPOS: RequestPOS
    ): ResponsePOS
}