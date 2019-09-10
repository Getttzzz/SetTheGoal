package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.api.RandomQuoteApi
import com.getz.setthegoal.datapart.entitylayer.QuoteDto

class QuoteRemoteDataSource(
    private val api: RandomQuoteApi
) : IQuoteDataSource {

    override suspend fun getQuoteAsync(lang: String, onResult: suspend (QuoteDto) -> Unit) {
        println("GETTTZZZ.QuoteRemoteDataSource.getQuoteAsync ---> api.getQuote")
        val quote = api.getQuote(
            method = "getQuote",
            format = "json",
            lang = lang
        )
        println("GETTTZZZ.QuoteRemoteDataSource.getQuoteAsync ---> body=${quote}")
        onResult(quote)
    }
}