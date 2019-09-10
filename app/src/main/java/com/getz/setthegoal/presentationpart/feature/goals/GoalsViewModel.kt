package com.getz.setthegoal.presentationpart.feature.goals

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUseCase
import com.getz.setthegoal.presentationpart.core.BaseVm
import kotlinx.coroutines.launch

class GoalsViewModel(
    private val getQuoteUseCase: IGetQuoteUseCase
) : BaseVm() {

    val quoteLD = MutableLiveData<Quote>()

    fun loadRandomQuote(lang: String) = launch {
        println("GETTTZZZ.GoalsViewModel.loadRandomQuote ---> getQuoteUseCase.invoke")
        getQuoteUseCase.invoke(lang, ::processError) { quote ->
            quoteLD.value = quote
        }
    }
}