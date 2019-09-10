package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.api.QuoteApiLanguageEnum
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

    override suspend fun getRandomQuoteAsync(
        lang: QuoteApiLanguageEnum,
        onResult: suspend (Quote) -> Unit
    ) {
        remoteDS.getQuoteAsync(lang) { quoteDto ->
            val quote = toDomainMapper.transform(quoteDto)
            onResult(quote)
        }
    }
}