package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.datasource.GoalRemoteDS
import com.getz.setthegoal.datapart.datasource.IGoalDS
import com.getz.setthegoal.datapart.datasource.IPartOfSpeechDS
import com.getz.setthegoal.datapart.datasource.IPhotoDS
import com.getz.setthegoal.datapart.datasource.IQuoteDS
import com.getz.setthegoal.datapart.datasource.ITranslatorDS
import com.getz.setthegoal.datapart.datasource.PartOfSpeechRemoteDS
import com.getz.setthegoal.datapart.datasource.PhotoRemoteDS
import com.getz.setthegoal.datapart.datasource.QuoteRemoteDS
import com.getz.setthegoal.datapart.datasource.TranslatorRemoteDS
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val dataSourceModule = Kodein.Module(ModulesNames.DATA_SOURCE_MODULE) {
    bind<IQuoteDS>() with singleton { QuoteRemoteDS(instance()) }
    bind<IPartOfSpeechDS>() with singleton { PartOfSpeechRemoteDS(instance()) }
    bind<ITranslatorDS>() with singleton { TranslatorRemoteDS(instance()) }
    bind<IPhotoDS>() with singleton { PhotoRemoteDS(instance()) }
    bind<IGoalDS>() with singleton { GoalRemoteDS(instance()) }
}