package com.getz.setthegoal.domainpart.core

interface ISuspendlessUseCase<in Request, out Response> {
    operator fun invoke(
        request: Request,
        onError: (Throwable) -> Unit,
        onResult: (Response) -> Unit
    )
}