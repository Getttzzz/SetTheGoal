package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.datapart.mapper.DataToDomainQuoteMapper
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Quote
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val domainMapperModule = Kodein.Module(ModulesNames.DOMAIN_TRANSFORMER_MODULE) {
    bind<Gandalf<QuoteDto, Quote>>() with provider { DataToDomainQuoteMapper() }
}