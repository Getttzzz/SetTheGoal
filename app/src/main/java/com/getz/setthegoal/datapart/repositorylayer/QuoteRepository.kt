package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.repositorylayer.IQuoteRepository

class QuoteRepository : BaseRepository(), IQuoteRepository {

    override suspend fun getRandomQuoteAsync(onResult: suspend (Quote) -> Unit) {
        //todo get data from remote
    }
}