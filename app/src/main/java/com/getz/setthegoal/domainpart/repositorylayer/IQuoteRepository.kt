package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.datapart.api.QuoteApiLanguageEnum
import com.getz.setthegoal.domainpart.core.IRepository
import com.getz.setthegoal.domainpart.entitylayer.Quote

interface IQuoteRepository : IRepository {
    suspend fun getRandomQuoteAsync(lang: QuoteApiLanguageEnum, onResult: suspend (Quote) -> Unit)
}