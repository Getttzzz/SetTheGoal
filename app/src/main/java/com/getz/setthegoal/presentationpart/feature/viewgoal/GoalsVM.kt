package com.getz.setthegoal.presentationpart.feature.viewgoal

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IGetGoalsUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import kotlinx.coroutines.launch
import java.util.Locale

class GoalsVM(
    private val getQuoteUC: IGetQuoteUC,
    private val getGoalsUC: IGetGoalsUC,
    private val presentationToDomainGoalMapper: Gandalf<List<Goal>, List<GoalUI>>
) : BaseVm() {

    val quoteLD = MutableLiveData<Quote>()

    val goalsLD = MutableLiveData<List<GoalUI>>()

    fun loadRandomQuote(locale: Locale) = launch {
        getQuoteUC.invoke(locale, ::processError) { quote ->
            quoteLD.value = quote
        }
    }

    fun loadGoals() {
        getGoalsUC.invoke(Unit, ::processError) { goals ->
            goalsLD.value = presentationToDomainGoalMapper.transform(goals)
        }
    }


}