package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.datapart.entitylayer.customexception.ResultWasEmptyException
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.interactorlayer.ICreateGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPhotoUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.entitylayer.PhotoUI
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.entitylayer.WordUI
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.Date
import java.util.Locale

const val CONST_FAMILY = "family"
const val CONST_MYSELF = "myself"

class CreateGoalVM(
    private val getPartsOfSpeechUC: IGetPartsOfSpeechUC,
    private val getPhotoUC: IGetPhotoUC,
    private val createGoalUC: ICreateGoalUC,
    private val toDomainGoalMapper: Gandalf<GoalUI, Goal>,
    private val gandalfWordsMapper: Gandalf<List<Word>, List<WordUI>>,
    private val gandalfPhotosMapper: Gandalf<List<Photo>, List<PhotoUI>>
) : BaseVm() {

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
}
