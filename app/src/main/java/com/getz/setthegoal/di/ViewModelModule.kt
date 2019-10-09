package com.getz.setthegoal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getz.setthegoal.presentationpart.feature.viewgoaldetails.ViewGoalVM
import com.getz.setthegoal.presentationpart.feature.viewgoals.GoalsVM
import org.kodein.di.Kodein
import org.kodein.di.TT
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val viewModelModule = Kodein.Module(ModulesNames.VIEW_MODEL_MODULE) {
    import(factoryModule)

    bind<ViewGoalVM>(tag = ViewGoalVM::class.java.simpleName) with provider {
        ViewGoalVM(instance(), instance(), instance())
    }

    bind<GoalsVM>(tag = GoalsVM::class.java.simpleName) with singleton {
        GoalsVM(
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

@Deprecated("need to remove")
class ViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        kodein.direct.Instance(TT(modelClass), modelClass.simpleName)
}