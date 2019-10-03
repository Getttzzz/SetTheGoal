package com.getz.setthegoal.presentationpart.feature.viewgoals

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetGoalsUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.feature.creategoal.CONST_FAMILY
import com.getz.setthegoal.presentationpart.feature.creategoal.CONST_MYSELF
import kotlinx.coroutines.launch
import java.util.Locale

class GoalsVM(
    private val getQuoteUC: IGetQuoteUC,
    private val getGoalsUC: IGetGoalsUC,
    private val deleteGoalUC: IDeleteGoalUC,
    private val presentationToDomainGoalMapper: Gandalf<List<Goal>, List<GoalUI>>
) : BaseVm() {

    val quoteLD = MutableLiveData<Quote>()

    val goalsForFamilyLD = MutableLiveData<List<GoalUI>>()
    val goalsForMyselfLD = MutableLiveData<List<GoalUI>>()

    fun loadRandomQuote(locale: Locale) = launch {
        getQuoteUC.invoke(locale, ::processError) { quote ->
            quoteLD.value = quote
        }
    }

    fun loadGoals() {
        getGoalsUC.invoke(Unit, ::processError) { goals ->
            val goalsUI = presentationToDomainGoalMapper.transform(goals)
            val goalsForFamily = goalsUI.filter { it.forWhom == CONST_FAMILY }
            val goalsForMyself = goalsUI.filter { it.forWhom == CONST_MYSELF }
            goalsForFamilyLD.value = goalsForFamily
            goalsForMyselfLD.value = goalsForMyself
        }
    }

    fun deleteGoal(goalId: String) = launch {
        deleteGoalUC.invoke(goalId, ::processError) {}
    }


}