package com.getz.setthegoal.domainpart.core

interface IRequestableUseCase<in Request, out Response> : IUseCase {
    suspend operator fun invoke(
        request: Request,
        onError: (Throwable) -> Unit,
        onResult: suspend (Response) -> Unit
    ): Any?
}