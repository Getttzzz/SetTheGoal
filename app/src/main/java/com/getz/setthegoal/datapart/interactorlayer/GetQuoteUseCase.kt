package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.api.QuoteApiLanguageEnum
import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUseCase
import com.getz.setthegoal.domainpart.repositorylayer.IQuoteRepository
import java.util.Locale

class GetQuoteUseCase(private val repository: IQuoteRepository) : BaseUseCase(), IGetQuoteUseCase {
    override suspend fun invoke(
        request: Locale,
        onError: (Throwable) -> Unit,
        onResult: suspend (Quote) -> Unit
    ) = withDefault(onError) {

        val lang = when (request) {
            QuoteApiLanguageEnum.EN.locale -> QuoteApiLanguageEnum.EN
            QuoteApiLanguageEnum.RU.locale -> QuoteApiLanguageEnum.RU
            else -> QuoteApiLanguageEnum.EN
        }

        repository.getRandomQuoteAsync(lang) { quote ->
            withUI(onError) { onResult(quote) }
        }
    }
}