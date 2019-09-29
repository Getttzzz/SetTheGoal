package com.getz.setthegoal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.feature.viewgoal.GoalsVM
import org.kodein.di.Kodein
import org.kodein.di.TT
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val viewModelModule = Kodein.Module(ModulesNames.VIEW_MODEL_MODULE) {
    import(factoryModule)

    bind<GoalsVM>(tag = GoalsVM::class.java.simpleName) with singleton {
        GoalsVM(
            instance(),
            instance(),
            instance()
        )
    }


    /**
     * Shared view model. That's why it's singleton
     * */
    //todo learn kodein scopes https://kodein.org/Kodein-DI/?6.4/core#_scope
    bind<CreateGoalVM>(tag = CreateGoalVM::class.java.simpleName) with
            singleton {
                CreateGoalVM(
                    instance(),
                    instance(),
                    instance(),
                    instance(),
                    instance(),
                    instance()
                )
            }
}

private val factoryModule = Kodein.Module(ModulesNames.FACTORY_VIEW_MODEL_MODULE) {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein) }
}

class ViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        kodein.direct.Instance(TT(modelClass), modelClass.simpleName)
}