package com.getz.setthegoal.datapart.core

import com.getz.setthegoal.domainpart.core.IUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

abstract class BaseUseCase : IUseCase {

    protected suspend fun <T> withDefault(onError: (Throwable) -> Unit, onResult: suspend () -> T) =
        coroutineScope {
            try {
                withContext(Dispatchers.Default) { onResult() }
            } catch (e: Throwable) {
                onError(e)
            }
        }

    protected suspend fun <T> withUI(onError: (Throwable) -> Unit, onResult: suspend () -> T) =
        try {
            withContext(Dispatchers.Main) { onResult() }
        } catch (e: Throwable) {
            onError(e)
        }
}