package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.datasource.IPartOfSpeechDataSource
import com.getz.setthegoal.datapart.datasource.IQuoteDataSource
import com.getz.setthegoal.datapart.datasource.PartOfSpeechRemoteDataSource
import com.getz.setthegoal.datapart.datasource.QuoteRemoteDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val dataSourceModule = Kodein.Module(ModulesNames.DATA_SOURCE_MODULE) {
    bind<IQuoteDataSource>() with singleton { QuoteRemoteDataSource(instance()) }
    bind<IPartOfSpeechDataSource>() with singleton { PartOfSpeechRemoteDataSource(instance()) }
}