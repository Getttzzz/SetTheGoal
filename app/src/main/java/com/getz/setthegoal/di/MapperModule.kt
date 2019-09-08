package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.datapart.transformer.DataToDomainQuoteTransformer
import com.getz.setthegoal.domainpart.core.OptimusPrime
import com.getz.setthegoal.domainpart.entitylayer.Quote
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val domainTransformerModule = Kodein.Module(ModulesNames.DOMAIN_TRANSFORMER_MODULE) {
    bind<OptimusPrime<QuoteDto, Quote>>() with provider { DataToDomainQuoteTransformer() }
}