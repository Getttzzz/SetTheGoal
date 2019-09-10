package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.api.QuoteApiLanguageEnum
import com.getz.setthegoal.datapart.entitylayer.QuoteDto

interface IQuoteDataSource {
    suspend fun getQuoteAsync(lang: QuoteApiLanguageEnum, onResult: suspend (QuoteDto) -> Unit)
}