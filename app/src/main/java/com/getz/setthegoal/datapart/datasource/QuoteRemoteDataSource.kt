package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.api.QuoteApiLanguageEnum
import com.getz.setthegoal.datapart.api.RandomQuoteApi
import com.getz.setthegoal.datapart.entitylayer.QuoteDto

class QuoteRemoteDataSource(
    private val api: RandomQuoteApi
) : IQuoteDataSource {

    override suspend fun getQuoteAsync(
        lang: QuoteApiLanguageEnum,
        onResult: suspend (QuoteDto) -> Unit
    ) {
        val quote = api.getQuote(
            method = "getQuote",
            format = "json",
            lang = lang.locale.language
        )
        onResult(quote)
    }
}