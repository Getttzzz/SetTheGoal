package com.getz.setthegoal.presentationpart.feature.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.di.AndroidScope
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.util.OkayEnum
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
const val DELAY_BETWEEN_WELCOME_WORDS = 500L


fun getWelcomeModule() = Kodein.Module(WELCOME_VM_MODULE) {
    bind<WelcomeVM>() with scoped<Fragment>(AndroidScope).singleton {
        WelcomeVM.getInstance(instance(), WelcomeVMF())
    }
}

//Todo change start guide, current guide is boring.
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

        //These sentences will be as a guide.
        val sentences = listOf(
            Pair(
                getString(R.string.opportunities),
                getString(R.string.opportunities_img)
            ),

            //About separation For myself and For family
            Pair(
                getString(R.string.endeavor),
                getString(R.string.endeavor_img)
            ),
            Pair(
                getString(R.string.for_family_or_for_yourself),
                getString(R.string.for_family_or_for_yourself_img)
            ),

            //About notification
            Pair(
                getString(R.string.app_sends_smart_notification),
                getString(R.string.app_sends_smart_notification_img)
            ),
            Pair(
                getString(R.string.interval_notification),
                getString(R.string.interval_notification_img)
            ),

            //About offline work
            Pair(
                getString(R.string.works_offline),
                getString(R.string.works_offline_img)
            ),
            Pair(
                getString(R.string.anywhere),
                getString(R.string.anywhere_img)
            )
        )
        vm.setSentences(sentences)
        vm.wordLD.observe(this, Observer { word -> tvWord.text = word })
        vm.memeLD.observe(this, Observer { memeUrl ->
            GlideApp.with(this)
                .load(memeUrl)
                .apply(RequestOptions().centerInside())
                .error(R.drawable.layer_list_bee)
                .into(ivMeme)
        })
        vm.seeOthersLD.observe(this, Observer {
            btnSeeNext.text = getString(OkayEnum.getRandomAgreement().strRes)
            btnSeeNext.isVisible = it
            ivMeme.isVisible = it
        })
        vm.thisIsTheEnd.observe(this, Observer {
            //            tvWord.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
//            tvWord.gravity = Gravity.START
//            tvWord.isAllCaps = false

//            val desiredHeight = resources.getDimensionPixelSize(R.dimen.svWordByWordHeight)
//            svWelcomeWords.layoutParams.height = desiredHeight

            btnSeeNext.text = getString(OkayEnum.getRandomAgreement().strRes)
            btnSeeNext.isVisible = true
            ivMeme.isVisible = true
            btnSeeNext.setSingleClickListener { bridge.openAuthScreen() }
        })
        btnSeeNext.setSingleClickListener { vm.seeNextSentence() }
        mcvWelcomeClose.setSingleClickListener { bridge.openAuthScreen() }

        vm.seeNextSentence()
    }
}


class WelcomeVM : BaseVm() {
    init {
        println("GETTTZZZ.WelcomeVM.init ---> this.hashCode=${this.hashCode()}")
    }

    val wordLD = MutableLiveData<String>()
    val memeLD = MutableLiveData<String>()
    val seeOthersLD = MutableLiveData<Boolean>()
    val thisIsTheEnd = MutableLiveData<Unit>()

    private val cachedSentences = LinkedList<Pair<String, String>>()
    private val bufferSentences = ArrayList<Pair<String, String>>()

    fun seeNextSentence() = launch {
        if (cachedSentences.isNotEmpty()) showNextSentenceWordByWord(cachedSentences)
    }

    fun setSentences(sentences: List<Pair<String, String>>) {
        cachedSentences.addAll(sentences)
        bufferSentences.addAll(sentences)
    }

    private suspend fun showNextSentenceWordByWord(sentences: LinkedList<Pair<String, String>>) {
        seeOthersLD.postValue(false)

        val firstSentence = sentences[0].first
        val memeUrl = sentences[0].second
        memeLD.postValue(memeUrl)

        sentences.removeAt(0)

        val words = firstSentence.split(" ")

        for (i in words.indices) {
            delay(DELAY_BETWEEN_WELCOME_WORDS)


            if (i == words.size - 1) {
                //last word in sentence came

                wordLD.postValue(words[i])
                delay(DELAY_BETWEEN_WELCOME_WORDS)
                wordLD.postValue(firstSentence)

                //if last sentence, triggers thisIsTheEnd live data
                if (sentences.size == 0) {
                    thisIsTheEnd.postValue(Unit)
                } else {
                    seeOthersLD.postValue(true)
                }
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