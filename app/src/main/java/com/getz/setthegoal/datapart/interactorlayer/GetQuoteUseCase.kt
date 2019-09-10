package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUseCase
import com.getz.setthegoal.domainpart.repositorylayer.IQuoteRepository

class GetQuoteUseCase(
    private val repository: IQuoteRepository
) : BaseUseCase(), IGetQuoteUseCase {
    override suspend fun invoke(
        request: String,
        onError: (Throwable) -> Unit,
        onResult: suspend (Quote) -> Unit
    ) = withDefault(onError) {
        repository.getRandomQuoteAsync(request) { quote ->
            withUI(onError) { onResult(quote) }
        }
    }
}