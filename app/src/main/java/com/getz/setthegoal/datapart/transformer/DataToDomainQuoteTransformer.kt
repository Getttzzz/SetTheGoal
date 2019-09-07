package com.getz.setthegoal.datapart.transformer

import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.domainpart.core.OptimusPrime
import com.getz.setthegoal.domainpart.entitylayer.Quote

class DataToDomainQuoteTransformer : OptimusPrime<QuoteDto, Quote> {
    override fun transform(source: QuoteDto) = Quote(
        source.quoteText ?: "",
        source.quoteAuthor ?: ""
    )
}