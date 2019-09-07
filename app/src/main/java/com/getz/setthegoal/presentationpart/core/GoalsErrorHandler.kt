package com.getz.setthegoal.presentationpart.core

class GoalsErrorHandler(private val vm: BaseVm) {
    fun handleError(error: Throwable) {
        vm.showError(error.localizedMessage)
    }
}