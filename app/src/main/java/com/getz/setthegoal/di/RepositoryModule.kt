package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.repositorylayer.PartOfSpeechRepository
import com.getz.setthegoal.datapart.repositorylayer.QuoteRepository
import com.getz.setthegoal.domainpart.repositorylayer.IPartOfSpeechRepository
import com.getz.setthegoal.domainpart.repositorylayer.IQuoteRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val repositoryModule = Kodein.Module(ModulesNames.REPOSITORY_MODULE) {
    bind<IQuoteRepository>() with singleton { QuoteRepository(instance(), instance()) }
    bind<IPartOfSpeechRepository>() with singleton {
        PartOfSpeechRepository(instance(), instance(), instance())
    }
}