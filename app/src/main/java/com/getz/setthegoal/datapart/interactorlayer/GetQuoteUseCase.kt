package com.getz.setthegoal.datapart.interactorlayer

import com.getz.setthegoal.datapart.core.BaseUseCase
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUseCase

//todo add repo
class GetQuoteUseCase(

) : BaseUseCase(), IGetQuoteUseCase {
    override suspend fun invoke(
        request: Unit,
        onError: (Throwable) -> Unit,
        onResult: suspend (Quote) -> Unit
    ) = withDefault(onError) {
        //repo.getQuote
    }
}