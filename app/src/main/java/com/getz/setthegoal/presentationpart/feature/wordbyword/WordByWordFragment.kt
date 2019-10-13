package com.getz.setthegoal.presentationpart.feature.wordbyword

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.getz.setthegoal.R
import com.getz.setthegoal.di.AndroidScope
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.util.say
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_word_by_word.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import java.util.LinkedList


const val WORD_BY_WORD_VM_MODULE = "WORD_BY_WORD_VM_MODULE"
const val INTERVAR_BETWEEN_WORDS = 1000L

fun getWordByWordModule() = Kodein.Module(WORD_BY_WORD_VM_MODULE) {
    bind<WordByWordVM>() with scoped<Fragment>(AndroidScope).singleton {
        WordByWordVM.getInstance(instance(), WordByWordVMF())
    }
}


class WordByWordFragment : BaseFragment(R.layout.fragment_word_by_word) {

    val vm: WordByWordVM by kodein.on(context = this).instance()

    override fun provideOverridingModule() = getWordByWordModule()

    companion object {
        private const val EXTRA_GOALS_FOR_TODAY_LOCAL = "EXTRA_GOALS_FOR_TODAY_LOCAL"
        fun getInstance(goals: List<GoalUI>) = WordByWordFragment().apply {
            arguments = bundleOf(EXTRA_GOALS_FOR_TODAY_LOCAL to goals)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val goals =
            arguments?.getParcelableArrayList(EXTRA_GOALS_FOR_TODAY_LOCAL) ?: emptyList<GoalUI>()
        vm.setGoals(goals)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.wordLD.observe(this, Observer { word ->
            println("GETTTZZZ.WordByWordFragment.onViewCreated ---> word=$word")
            tvWord.text = word
        })
        vm.seeOthersLD.observe(this, Observer { btnSeeOthers.isVisible = it })
        vm.thisIsTheEnd.observe(this, Observer {

            tvThisIsTheEnd.isVisible = true
        })
        vm.errorLD.observe(this, Observer { this.say(it) })

        vm.seeNextGoal()

        btnSeeOthers.setSingleClickListener { vm.seeNextGoal() }
    }
}


class WordByWordVM : BaseVm() {
    init {
        println("GETTTZZZ.WordByWordVM.init ---> this.hashCode=${this.hashCode()}")
    }

    val wordLD = MutableLiveData<String>()
    val seeOthersLD = MutableLiveData<Boolean>()
    val thisIsTheEnd = MutableLiveData<Unit>()

    val cachedGoals = LinkedList<GoalUI>()
    val bufferGoals = ArrayList<GoalUI>()

    fun seeNextGoal() = launch {
        if (cachedGoals.isNotEmpty()) {
            showNextWord(cachedGoals)
        }
    }

    private suspend fun showNextWord(goals: LinkedList<GoalUI>) {
        seeOthersLD.postValue(false)

        val firstGoal = goals[0]
        goals.removeAt(0)

        val words = firstGoal.text.split(" ")

        for (i in words.indices) {
            println("GETTTZZZ.WordByWordVM.showNextWord ---> i=$i word[i]=${words[i]}")
            delay(INTERVAR_BETWEEN_WORDS)


            if (i == words.size - 1) {

                wordLD.postValue(words[i])
                delay(INTERVAR_BETWEEN_WORDS)

                wordLD.postValue(firstGoal.text)
                if (goals.size == 0) {
                    val allText = StringBuilder()
                    bufferGoals.forEach { g ->
                        allText.append(g.text)
                        allText.append("\n")
                    }
                    wordLD.postValue(allText.toString())
                    thisIsTheEnd.postValue(Unit)
                } else seeOthersLD.postValue(true)
            } else {
                wordLD.postValue(words[i])
            }
        }
    }

    fun setGoals(goals: List<GoalUI>) {
        cachedGoals.addAll(goals)
        bufferGoals.addAll(goals)
    }

    companion object {
        fun getInstance(fragment: Fragment, factory: WordByWordVMF) =
            ViewModelProviders.of(fragment, factory)[WordByWordVM::class.java]
    }
}


class WordByWordVMF : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        WordByWordVM() as T
}