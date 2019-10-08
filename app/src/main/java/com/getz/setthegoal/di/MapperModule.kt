package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.entitylayer.GoalDto
import com.getz.setthegoal.datapart.entitylayer.QuoteDto
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse
import com.getz.setthegoal.datapart.mapper.DataToDomainGoalMapper
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
import com.getz.setthegoal.presentationpart.mapper.DomainToPresentationGoalMapper
import com.getz.setthegoal.presentationpart.mapper.DomainToPresentationPhotoMapper
import com.getz.setthegoal.presentationpart.mapper.DomainToPresentationWordMapper
import com.getz.setthegoal.presentationpart.mapper.PresentationToDomainGoalMapper
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val mapperModule = Kodein.Module(ModulesNames.MAPPER_MODULE) {
    import(dataToDomain)
    import(domainToData)
    import(presentationToDomain)
    import(domainToPresentation)
}

/*     Data
 *      ||
 *      \/
 *    Domain
 * */
private val dataToDomain = Kodein.Module(ModulesNames.DATA_TO_DOMAIN_MAPPER_MODULE) {
    bind<Gandalf<QuoteDto, Quote>>() with provider { DataToDomainQuoteMapper() }
    bind<Gandalf<ResponsePOS, List<Word>>>(tag = DataToDomainPartOfSpeechMapper::class.java.simpleName) with provider { DataToDomainPartOfSpeechMapper() }
    bind<Gandalf<List<Word>, List<Word>>>(tag = DataToDomainPartOfSpeechFilter::class.java.simpleName) with provider { DataToDomainPartOfSpeechFilter() }
    bind<Gandalf<SearchPhotoResponse, List<Photo>>>() with provider { DataToDomainPhotoMapper() }
    bind<Gandalf<List<GoalDto>, List<Goal>>>() with provider { DataToDomainGoalMapper() }
}

/*     Data
 *      /\
 *      ||
 *    Domain
 * */
private val domainToData = Kodein.Module(ModulesNames.DOMAIN_TO_DATA_MAPPER_MODULE) {
    bind<Gandalf<Goal, GoalDto>>() with provider { DomainToDataGoalMapper(instance()) }
}

/*    Domain
 *      ||
 *      \/
 * Presentation
 * */
private val domainToPresentation = Kodein.Module(ModulesNames.DOMAIN_TO_PRESENT_MAPPER_MODULE) {
    bind<Gandalf<List<Word>, List<WordUI>>>() with provider { DomainToPresentationWordMapper() }
    bind<Gandalf<List<Photo>, List<PhotoUI>>>() with provider { DomainToPresentationPhotoMapper() }
    bind<Gandalf<List<Goal>, List<GoalUI>>>() with provider { DomainToPresentationGoalMapper() }
}

/*    Domain
 *      /\
 *      ||
 * Presentation
 * */
private val presentationToDomain = Kodein.Module(ModulesNames.PRESENT_TO_DOMAIN_MAPPER_MODULE) {
    bind<Gandalf<GoalUI, Goal>>() with provider { PresentationToDomainGoalMapper() }
}

