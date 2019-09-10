package com.getz.setthegoal.presentationpart.feature.goals

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUseCase
import com.getz.setthegoal.presentationpart.core.BaseVm
import kotlinx.coroutines.launch
import java.util.Locale

class GoalsViewModel(
    private val getQuoteUseCase: IGetQuoteUseCase
) : BaseVm() {

    val quoteLD = MutableLiveData<Quote>()

    fun loadRandomQuote(locale: Locale) = launch {
        getQuoteUseCase.invoke(locale, ::processError) { quote ->
            quoteLD.value = quote
        }
    }
}