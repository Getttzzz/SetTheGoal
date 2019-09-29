package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.lifecycle.MutableLiveData
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
import com.getz.setthegoal.presentationpart.entitylayer.UrlsUI
import com.getz.setthegoal.presentationpart.entitylayer.WordUI
import kotlinx.coroutines.launch
import java.util.Locale

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

    var isForFamily = false

    var writtenGoalText: String = ""
    var selectedPhoto: PhotoUI = PhotoUI(UrlsUI("", "", "", "", ""), "", "", "", false)
    var selectedSubTasks: List<SubGoalUI> = listOf()
    var selectedDeadline: String = ""

    fun recognizePartsOfSpeech() = launch {
        getPartsOfSpeechUC.invoke(writtenGoalText, ::processError) { recognizedWords ->
            recognizedWordsLD.value = gandalfWordsMapper.transform(recognizedWords)
        }
    }

    fun getPhotos(selectedWord: String, default: Locale) = launch {
        loadingPhotosLD.value = true
        val request = Pair(selectedWord, default)
        getPhotoUC.invoke(request, ::processError) { photos ->
            println("GETTTZZZ.CreateGoalVM.getPhotos ---> photos=$photos")
            photosResultLD.value = gandalfPhotosMapper.transform(photos)
            loadingPhotosLD.value = false
        }
    }

    fun saveGoal() = launch {
        val goalUI = GoalUI(
            text = writtenGoalText,
            photo = selectedPhoto,
            subGoals = selectedSubTasks,
            deadline = selectedDeadline,
            forWhom = if (isForFamily) "family" else "myself",
            done = false
        )
        val goalDomain = toDomainGoalMapper.transform(goalUI)
        createGoalUC.invoke(goalDomain, ::processError) { success ->
            println("GETTTZZZ.CreateGoalVM.saveGoal ---> success=$success")
        }
    }

}
