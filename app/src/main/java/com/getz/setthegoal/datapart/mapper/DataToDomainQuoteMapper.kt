package com.getz.setthegoal.datapart.mapper

import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Quote

class DataToDomainQuoteMapper : Gandalf<QuoteDto, Quote> {
    override fun transform(source: QuoteDto) = Quote(
        source.quoteText ?: "",
        source.quoteAuthor ?: ""
    )
}