package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.interactorlayer.GetQuoteUseCase
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val useCaseModule = Kodein.Module(ModulesNames.USE_CASE_MODULE) {
    bind<IGetQuoteUseCase>() with singleton { GetQuoteUseCase(instance()) }
}