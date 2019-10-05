package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.interactorlayer.CreateGoalUC
import com.getz.setthegoal.datapart.interactorlayer.DeleteGoalUC
import com.getz.setthegoal.datapart.interactorlayer.GetGoalsUC
import com.getz.setthegoal.datapart.interactorlayer.GetPartsOfSpeechUC
import com.getz.setthegoal.datapart.interactorlayer.GetPhotoUC
import com.getz.setthegoal.datapart.interactorlayer.GetQuoteUC
import com.getz.setthegoal.datapart.interactorlayer.UpdateGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.ICreateGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetGoalsUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPhotoUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUC
import com.getz.setthegoal.domainpart.interactorlayer.IUpdateGoalUC
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val useCaseModule = Kodein.Module(ModulesNames.USE_CASE_MODULE) {
    bind<IGetQuoteUC>() with singleton { GetQuoteUC(instance()) }
    bind<IGetPartsOfSpeechUC>() with singleton { GetPartsOfSpeechUC(instance()) }
    bind<IGetPhotoUC>() with singleton { GetPhotoUC(instance(), instance()) }
    bind<ICreateGoalUC>() with singleton { CreateGoalUC(instance(), instance()) }
    bind<IUpdateGoalUC>() with singleton { UpdateGoalUC(instance()) }
    bind<IGetGoalsUC>() with singleton { GetGoalsUC(instance()) }
    bind<IDeleteGoalUC>() with singleton { DeleteGoalUC(instance()) }
}