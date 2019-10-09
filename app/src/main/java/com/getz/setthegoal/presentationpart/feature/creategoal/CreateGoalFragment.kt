package com.getz.setthegoal.presentationpart.feature.creategoal

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.getz.setthegoal.R
import com.getz.setthegoal.datapart.entitylayer.customexception.ResultWasEmptyException
import com.getz.setthegoal.di.AndroidScope
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.interactorlayer.ICreateGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPhotoUC
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.entitylayer.PhotoUI
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.entitylayer.WordUI
import com.getz.setthegoal.presentationpart.util.addOnPageSelectedListener
import com.getz.setthegoal.presentationpart.util.say
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.swipeLeft
import com.getz.setthegoal.presentationpart.util.swipeRight
import kotlinx.android.synthetic.main.fragment_create_goal.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import java.net.UnknownHostException
import java.util.Date
import java.util.Locale


const val CONST_FAMILY = "family"
const val CONST_MYSELF = "myself"
private const val CREATE_GOAL_VM_MODULE = "CREATE_GOAL_VM_MODULE"

fun getCreateGoalModule() = Kodein.Module(CREATE_GOAL_VM_MODULE) {
    bind<CreateGoalVM>() with scoped<Fragment>(AndroidScope).singleton {
        CreateGoalVM.getInstance(
            instance(),
            CreateGoalVMF(instance(), instance(), instance(), instance(), instance(), instance())
        )
    }
}

class CreateGoalFragment : BaseFragment(R.layout.fragment_create_goal) {

    val vm: CreateGoalVM by kodein.on(context = this).instance()
    lateinit var bridge: CreateGoalBridge
    private var keyboardListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    override fun provideOverridingModule() = getCreateGoalModule()

    companion object {
        fun getInstance() = CreateGoalFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as CreateGoalBridge
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("GETTTZZZ.CreateGoalFragment.onCreate ---> vm=${vm} vm.hashCode=${vm.hashCode()}")
//        val kit: Kitten by kodein.on(context = this).instance()
//        val kitten = direct.instance<Kitten>() doesn't work with scope =((((
//        direct.instance()
//        kodein.instance()
//        kodein.baseKodein.container.tree.bindings[]
//        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupLD()
    }

//    override fun onResume() {
//        super.onResume()
//        //todo fix keyboard opening
//        clRootCreateGoal.doOnLayout {}
//        keyboardListener = clRootCreateGoal.addKeyboardListener { isOpened ->
//            vm.keyboardListenerLD.value = isOpened
//
//            if (btnNext != null) {
//                btnNext.isVisible = !isOpened
//            }
//        }
//    }
//
//    override fun onPause() {
//        clRootCreateGoal.removeKeyboardListener(keyboardListener)
//        keyboardListener = null
//        super.onPause()
//    }

    private fun setupLD() {
        vm.nextButtonSharedLD.observe(this, Observer { enabled ->
            btnNext.isEnabled = enabled
        })
        vm.pressNextSharedLD.observe(this, Observer { btnNext.performClick() })
        vm.errorLD.observe(this, Observer { this.say(it) })
    }

    private fun setupViewPager() {
        selectPage(CreateGoalPagerAdapter.WRITE_GOAL_TAB_POSITION)
        vpCreateGoal.adapter = CreateGoalPagerAdapter(childFragmentManager)
        vpCreateGoal.addOnPageSelectedListener { position -> selectPage(position) }
        vpCreateGoal.offscreenPageLimit = STEPS_TO_CREATE
        tlCreateGoal.setupWithViewPager(vpCreateGoal, true)
    }

    private fun selectPage(position: Int) {
        when (position) {
            CreateGoalPagerAdapter.WRITE_GOAL_TAB_POSITION -> {
                btnNext.text = getString(R.string.next)
                btnPrevious.text = getString(R.string.close)

                btnNext.setSingleClickListener {
                    vm.recognizePartsOfSpeech()
                    vpCreateGoal.swipeRight(position)
                }
                btnPrevious.setSingleClickListener { bridge.closeCreateFragment() }
            }
            CreateGoalPagerAdapter.APPLY_PICTURE_TAB_POSITION -> {
                btnNext.text = getString(R.string.next)
                btnPrevious.text = getString(R.string.previous)

                btnNext.setSingleClickListener { vpCreateGoal.swipeRight(position) }
                btnPrevious.setSingleClickListener { vpCreateGoal.swipeLeft(position) }
            }
            CreateGoalPagerAdapter.APPLY_SUBTASKS_TAB_POSITION -> {
                btnNext.text = getString(R.string.next)
                btnPrevious.text = getString(R.string.previous)

                btnNext.setSingleClickListener { vpCreateGoal.swipeRight(position) }
                btnPrevious.setSingleClickListener { vpCreateGoal.swipeLeft(position) }
            }
            CreateGoalPagerAdapter.APPLY_DEADLINE_TAB_POSITION -> {
                btnNext.text = getString(R.string.save)
                btnPrevious.text = getString(R.string.previous)

                btnNext.setSingleClickListener {
                    vm.saveGoal()
                    vpCreateGoal.swipeRight(position)
                    bridge.scrollToAppropriateTab(vm.isForFamily)
                }
                btnPrevious.setSingleClickListener { vpCreateGoal.swipeLeft(position) }
            }
            CreateGoalPagerAdapter.APPLY_FINISH_TAB_POSITION -> {
                btnNext.text = getString(R.string.done)
                btnPrevious.text = getString(R.string.close)

                btnNext.setSingleClickListener { bridge.closeCreateFragment() }
                btnPrevious.setSingleClickListener { bridge.closeCreateFragment() }
            }
        }
    }
}

class CreateGoalVM(
    private val getPartsOfSpeechUC: IGetPartsOfSpeechUC,
    private val getPhotoUC: IGetPhotoUC,
    private val createGoalUC: ICreateGoalUC,
    private val toDomainGoalMapper: Gandalf<GoalUI, Goal>,
    private val gandalfWordsMapper: Gandalf<List<Word>, List<WordUI>>,
    private val gandalfPhotosMapper: Gandalf<List<Photo>, List<PhotoUI>>
) : BaseVm() {

    init {
        println("GETTTZZZ.CreateGoalVM ---> this=${this.hashCode()}")
    }

    override fun onCleared() {
        super.onCleared()
        println("GETTTZZZ.CreateGoalVM.onCleared ---> this=${this.hashCode()}")
    }

    val nextButtonSharedLD = MutableLiveData<Boolean>()
    val pressNextSharedLD = MutableLiveData<Unit>()
    val recognizedWordsLD = MutableLiveData<List<WordUI>>()
    val keyboardListenerLD = MutableLiveData<Boolean>()
    val photosResultLD = MutableLiveData<List<PhotoUI>>()
    val loadingPhotosLD = MutableLiveData<Boolean>()
    val loadingWordsLD = MutableLiveData<Boolean>()
    val photoWasEmptyLD = MutableLiveData<Boolean>()

    var isForFamily = false
    var writtenGoalText: String = ""
    var selectedPhoto: PhotoUI? = null
    var selectedSubTasks: List<SubGoalUI> = arrayListOf()
    var selectedDeadline: String = ""

    fun recognizePartsOfSpeech() = launch {
        loadingWordsLD.value = true
        getPartsOfSpeechUC.invoke(writtenGoalText, { error ->
            loadingWordsLD.value = false
            when (error) {
                is ResultWasEmptyException -> loadingWordsLD.value = false
                is UnknownHostException -> loadingWordsLD.value = false
                else -> errorLD.value = error.localizedMessage
            }
        }, { recognizedWords ->
            loadingWordsLD.value = false
            recognizedWordsLD.value = gandalfWordsMapper.transform(recognizedWords)
        })
    }

    fun getPhotos(selectedWord: String, default: Locale) = launch {
        loadingPhotosLD.value = true
        photoWasEmptyLD.value = false
        val request = Pair(selectedWord, default)
        getPhotoUC.invoke(request, { error ->
            loadingPhotosLD.value = false
            when (error) {
                is ResultWasEmptyException -> photoWasEmptyLD.value = true
                is UnknownHostException -> loadingPhotosLD.value = false
                else -> errorLD.value = error.localizedMessage
            }
        }, { photos ->
            photosResultLD.value = gandalfPhotosMapper.transform(photos)
            loadingPhotosLD.value = false
        })
    }

    fun saveGoal() = launch {
        val goalUI = GoalUI(
            goalId = "",
            text = writtenGoalText,
            photo = selectedPhoto,
            subGoals = selectedSubTasks,
            deadline = selectedDeadline,
            forWhom = if (isForFamily) CONST_FAMILY else CONST_MYSELF,
            done = false,
            createdAt = Date(),
            updatedAt = Date()
        )

        val goalDomain = toDomainGoalMapper.transform(goalUI)

        createGoalUC.invoke(goalDomain, ::processError) {}

        writtenGoalText = ""
        selectedPhoto = null
        selectedSubTasks = arrayListOf()
        selectedDeadline = ""
        isForFamily = false
    }

    fun validateDeadline() {
        nextButtonSharedLD.value = selectedDeadline.isNotEmpty()
    }

    fun validateSubGoal() {
        nextButtonSharedLD.value = true
    }

    fun validatePhoto() {
        nextButtonSharedLD.value = true
    }

    companion object {
        fun getInstance(fragment: Fragment, factory: CreateGoalVMF) =
            ViewModelProviders.of(fragment, factory)[CreateGoalVM::class.java]
    }
}

class CreateGoalVMF(
    private val getPartsOfSpeechUC: IGetPartsOfSpeechUC,
    private val getPhotoUC: IGetPhotoUC,
    private val createGoalUC: ICreateGoalUC,
    private val toDomainGoalMapper: Gandalf<GoalUI, Goal>,
    private val gandalfWordsMapper: Gandalf<List<Word>, List<WordUI>>,
    private val gandalfPhotosMapper: Gandalf<List<Photo>, List<PhotoUI>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CreateGoalVM(
            getPartsOfSpeechUC,
            getPhotoUC,
            createGoalUC,
            toDomainGoalMapper,
            gandalfWordsMapper,
            gandalfPhotosMapper
        ) as T
}