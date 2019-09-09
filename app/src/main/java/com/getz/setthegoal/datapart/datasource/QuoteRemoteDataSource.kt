package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.api.RandomQuoteApi
import com.getz.setthegoal.datapart.entitylayer.QuoteDto

class QuoteRemoteDataSource(
    private val api: RandomQuoteApi
) : IQuoteDataSource {

    override suspend fun getQuoteAsync(lang: String, onResult: suspend (QuoteDto) -> Unit) {
        val quote = api.getQuote(
            method = "getQuote",
            format = "jsonp",
            jsonp = "parseQuote",
            lang = lang
        )
        println("GETZ.QuoteRemoteDataSource.getQuoteAsync ---> isSuccessful=${quote.isSuccessful}")
        println("GETZ.QuoteRemoteDataSource.getQuoteAsync ---> body=${quote.body()}")
//        onResult(quote)
    }
}