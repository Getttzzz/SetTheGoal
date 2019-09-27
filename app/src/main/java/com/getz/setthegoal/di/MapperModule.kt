package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse
import com.getz.setthegoal.datapart.mapper.DataToDomainPartOfSpeechFilter
import com.getz.setthegoal.datapart.mapper.DataToDomainPartOfSpeechMapper
import com.getz.setthegoal.datapart.mapper.DataToDomainPhotoMapper
import com.getz.setthegoal.datapart.mapper.DataToDomainQuoteMapper
import com.getz.setthegoal.datapart.mapper.DomainToDataGoalMapper
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.entitylayer.PhotoUI
import com.getz.setthegoal.presentationpart.entitylayer.WordUI
import com.getz.setthegoal.presentationpart.mapper.DomainToPresentationPhotoMapper
import com.getz.setthegoal.presentationpart.mapper.DomainToPresentationWordMapper
import com.getz.setthegoal.presentationpart.mapper.PresentationToDomainGoalMapper
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val mapperModule = Kodein.Module(ModulesNames.MAPPER_MODULE) {
    import(toDomainMapperModule)
    import(toDataMapperModule)
    import(toPresentationMapperModule)
}

private val toDomainMapperModule = Kodein.Module(ModulesNames.TO_DOMAIN_MAPPER_MODULE) {
    bind<Gandalf<QuoteDto, Quote>>() with provider { DataToDomainQuoteMapper() }
    bind<Gandalf<ResponsePOS, List<Word>>>() with provider { DataToDomainPartOfSpeechMapper() }
    bind<Gandalf<List<Word>, List<Word>>>() with provider { DataToDomainPartOfSpeechFilter() }
    bind<Gandalf<SearchPhotoResponse, List<Photo>>>() with provider { DataToDomainPhotoMapper() }
    bind<Gandalf<GoalUI, Goal>>() with provider { PresentationToDomainGoalMapper() }
}

private val toDataMapperModule = Kodein.Module(ModulesNames.TO_DATA_MAPPER_MODULE) {
    bind<Gandalf<Goal, GoalDto>>() with provider { DomainToDataGoalMapper(instance()) }
}

private val toPresentationMapperModule = Kodein.Module(ModulesNames.TO_PRESENTATION_MAPPER_MODULE) {
    bind<Gandalf<List<Word>, List<WordUI>>>() with provider { DomainToPresentationWordMapper() }
    bind<Gandalf<List<Photo>, List<PhotoUI>>>() with provider { DomainToPresentationPhotoMapper() }
}