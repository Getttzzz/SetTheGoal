package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.repositorylayer.GoalRepository
import com.getz.setthegoal.datapart.repositorylayer.PartOfSpeechRepository
import com.getz.setthegoal.datapart.repositorylayer.PhotoRepository
import com.getz.setthegoal.datapart.repositorylayer.QuoteRepository
import com.getz.setthegoal.datapart.repositorylayer.TranslatorRepository
import com.getz.setthegoal.domainpart.repositorylayer.IGoalRepository
import com.getz.setthegoal.domainpart.repositorylayer.IPartOfSpeechRepository
import com.getz.setthegoal.domainpart.repositorylayer.IPhotoRepository
import com.getz.setthegoal.domainpart.repositorylayer.IQuoteRepository
import com.getz.setthegoal.domainpart.repositorylayer.ITranslatorRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val repositoryModule = Kodein.Module(ModulesNames.REPOSITORY_MODULE) {
    bind<IQuoteRepository>() with singleton { QuoteRepository(instance(), instance()) }
    bind<IPartOfSpeechRepository>() with singleton {
        PartOfSpeechRepository(instance(), instance(), instance())
    }
    bind<IPhotoRepository>() with singleton { PhotoRepository(instance(), instance()) }
    bind<ITranslatorRepository>() with singleton { TranslatorRepository(instance()) }
    bind<IGoalRepository>() with singleton {
        GoalRepository(
            instance(),
            instance(),
            instance(),
            instance()
        )
    }
}