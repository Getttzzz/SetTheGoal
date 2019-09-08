package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.core.IRepository
import com.getz.setthegoal.domainpart.entitylayer.Quote

interface IQuoteRepository : IRepository {
    suspend fun getRandomQuoteAsync(onResult: suspend (Quote) -> Unit)
}