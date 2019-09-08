package com.getz.setthegoal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getz.setthegoal.presentationpart.feature.goals.GoalsViewModel
import org.kodein.di.Kodein
import org.kodein.di.TT
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val viewModelModule = Kodein.Module(ModulesNames.VIEW_MODEL_MODULE) {
    import(factoryModule)

    bind<GoalsViewModel>(tag = GoalsViewModel::class.java.simpleName) with provider {
        GoalsViewModel()
    }
}

private val factoryModule = Kodein.Module(ModulesNames.FACTORY_VIEW_MODEL_MODULE) {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein) }
}

class ViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        kodein.direct.Instance(TT(modelClass), modelClass.simpleName)
}