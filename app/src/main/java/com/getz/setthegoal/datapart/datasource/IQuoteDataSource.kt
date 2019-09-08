package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.QuoteDto

interface IQuoteDataSource {
    suspend fun getQuoteAsync(lang: String, onResult: suspend (QuoteDto) -> Unit)
}