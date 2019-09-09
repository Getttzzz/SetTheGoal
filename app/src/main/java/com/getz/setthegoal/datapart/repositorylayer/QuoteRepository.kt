package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.datapart.datasource.IQuoteDataSource
import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.repositorylayer.IQuoteRepository

class QuoteRepository(
    private val toDomainMapper: Gandalf<QuoteDto, Quote>,
    private val remoteDS: IQuoteDataSource
) : BaseRepository(), IQuoteRepository {

    override suspend fun getRandomQuoteAsync(onResult: suspend (Quote) -> Unit) {
        //todo get data from remote

    }
}