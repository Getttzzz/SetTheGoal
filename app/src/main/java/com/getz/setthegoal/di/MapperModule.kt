package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse
import com.getz.setthegoal.datapart.mapper.DataToDomainPartOfSpeechFilter
import com.getz.setthegoal.datapart.mapper.DataToDomainPartOfSpeechMapper
import com.getz.setthegoal.datapart.mapper.DataToDomainPhotoMapper
import com.getz.setthegoal.datapart.mapper.DataToDomainQuoteMapper
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.entitylayer.Word
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val domainMapperModule = Kodein.Module(ModulesNames.DOMAIN_TRANSFORMER_MODULE) {
    bind<Gandalf<QuoteDto, Quote>>() with provider { DataToDomainQuoteMapper() }
    bind<Gandalf<ResponsePOS, List<Word>>>() with provider { DataToDomainPartOfSpeechMapper() }
    bind<Gandalf<List<Word>, List<Word>>>() with provider { DataToDomainPartOfSpeechFilter() }
    bind<Gandalf<SearchPhotoResponse, List<Photo>>>() with provider { DataToDomainPhotoMapper() }
}