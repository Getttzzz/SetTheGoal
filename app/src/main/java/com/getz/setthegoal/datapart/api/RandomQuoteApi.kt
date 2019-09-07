package com.getz.setthegoal.datapart.api

import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomQuoteApi {

    //https://api.forismatic.com/api/1.0/?method=getQuote&format=jsonp&jsonp=parseQuote&lang=en

    @GET
    suspend fun getQuote(
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("jsonp") jsonp: String,
        @Query("lang") lang: String
    ): Response<QuoteDto>
}