package com.getz.setthegoal.presentationpart.feature.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
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
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import java.util.LinkedList

const val WELCOME_VM_MODULE = "WELCOME_VM_MODULE"
const val DELAY_BETWEEN_WELCOME_WORDS = 600L


fun getWelcomeModule() = Kodein.Module(WELCOME_VM_MODULE) {
    bind<WelcomeVM>() with scoped<Fragment>(AndroidScope).singleton {
        WelcomeVM.getInstance(instance(), WelcomeVMF())
    }
}


class WelcomeFragment : BaseFragment(R.layout.fragment_welcome) {

    val vm: WelcomeVM by kodein.on(context = this).instance()
    private lateinit var bridge: WelcomeBridge

    override fun provideOverridingModule() = getWelcomeModule()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as WelcomeBridge
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Todo create pre welcome screen with this text and birds.json animation.
        //Stop flying aimlessly
        //Get a conscious life
        //birds paint to gray color
        //btn "Let's go" make accent color

        //These sentences will be as a guide.
        val sentences = listOf(
            "Hello, a few words about opportunities of the app",

            //About separation For myself and For family
            "It is a very important to figure out for whom you will be endeavor",
            "I've made a two entities: for yourself and for your family",

            //About notification
            "App can send a notification about each goal",
            "While creating a goal you choose appropriate interval between getting a notification",
            "You control how often the goal will disturb you",

            //No category, short description
            "You forget goals, app reminds",
            "You lose direction, app restore",

            //About offline work
            "App can work offline",
            "create see remember your objectives anywhere!"
        )
        vm.setSentences(sentences)

        //todo change UI according to displaying WordByWord logic
        vm.wordLD.observe(this, Observer { word -> })

        btnSkip.setSingleClickListener { bridge.openAuthScreen() }
    }
}


class WelcomeVM : BaseVm() {
    init {
        println("GETTTZZZ.WelcomeVM.init ---> this.hashCode=${this.hashCode()}")
    }

    val wordLD = MutableLiveData<String>()
    val seeOthersLD = MutableLiveData<Boolean>()
    val thisIsTheEnd = MutableLiveData<Unit>()

    private val cachedSentences = LinkedList<String>()
    private val bufferSentences = ArrayList<String>()

    fun seeNextSentence() = launch {
        if (cachedSentences.isNotEmpty()) showNextSentenceWordByWord(cachedSentences)
    }

    fun setSentences(sentences: List<String>) {
        cachedSentences.addAll(sentences)
        bufferSentences.addAll(sentences)
    }

    private suspend fun showNextSentenceWordByWord(sentences: LinkedList<String>) {
        seeOthersLD.postValue(false)

        val firstSentence = sentences[0]
        sentences.removeAt(0)

        val words = firstSentence.split(" ")

        for (i in words.indices) {
            delay(DELAY_BETWEEN_WELCOME_WORDS)


            if (i == words.size - 1) {

                wordLD.postValue(words[i])
                delay(DELAY_BETWEEN_WELCOME_WORDS)

                wordLD.postValue(firstSentence)
                if (sentences.size == 0) {
                    val allText = StringBuilder()
                    for (j in bufferSentences.indices) {
                        val s = bufferSentences[j]
                        allText.append(j + 1)
                        allText.append(". ")
                        allText.append(s)
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

    companion object {
        fun getInstance(fragment: Fragment, factory: WelcomeVMF) =
            ViewModelProviders.of(fragment, factory)[WelcomeVM::class.java]
    }
}


class WelcomeVMF : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        WelcomeVM() as T
}