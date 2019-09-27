package com.getz.setthegoal.domainpart.core

interface IWritableUseCase<in Entity, out Response> {
    suspend operator fun invoke(
        writableEntity: Entity,
        onError: (Throwable) -> Unit,
        onResult: suspend (Response) -> Unit
    ): Any?
}