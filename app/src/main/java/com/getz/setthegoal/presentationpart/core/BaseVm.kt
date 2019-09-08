package com.getz.setthegoal.presentationpart.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseVm : ViewModel(), CoroutineScope {

    val errorLD = MutableLiveData<String>()

    private val errorHandler: GoalsErrorHandler by lazy(LazyThreadSafetyMode.NONE) {
        GoalsErrorHandler(this)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +
                Job() +
                CoroutineName("SetTheGoalCoroutine") +
                CoroutineExceptionHandler { context, exception ->
                    errorHandler.handleError(exception)
                }

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    fun showError(errorText: String) {
        errorLD.value = errorText
    }
}