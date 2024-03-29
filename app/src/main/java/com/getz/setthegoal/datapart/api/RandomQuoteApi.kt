package com.getz.setthegoal.datapart.api

import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface RandomQuoteApi {
    /**
     * That said, if you really want your base URL to be the full path you can use @GET(".")
     * to declare that your final URL is the same as your base URL.
     * */
    @GET(".")
    suspend fun getQuote(
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("lang") lang: String
    ): QuoteDto
}

/**
 * RandomQuoteApi works only with two languages.
 * */
enum class QuoteApiLanguageEnum(val locale: Locale) {
    EN(Locale.ENGLISH),
    RU(Locale("ru", "RU"));
}