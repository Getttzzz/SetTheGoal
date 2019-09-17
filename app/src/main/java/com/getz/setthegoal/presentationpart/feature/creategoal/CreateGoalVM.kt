package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPhotoUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import kotlinx.coroutines.launch
import java.util.Locale

class CreateGoalVM(
    val getPartsOfSpeechUC: IGetPartsOfSpeechUC,
    val getPhotoUC: IGetPhotoUC
) : BaseVm() {

    val nextButtonSharedLD = MutableLiveData<Boolean>()
    val pressNextSharedLD = MutableLiveData<Unit>()
    val recognizedWordsLD = MutableLiveData<List<Word>>()
    val keyboardListenerLD = MutableLiveData<Boolean>()

    var isForFamily = false

    lateinit var writtenGoalText: String
    lateinit var selectedImageUrl: String
    lateinit var selectedDeadline: String
    lateinit var selectedSubTasks: ArrayList<String>

    fun recognizePartsOfSpeech() = launch {
        getPartsOfSpeechUC.invoke(writtenGoalText, ::processError) { recognizedWords ->
            recognizedWordsLD.value = recognizedWords
        }
    }

    fun getPhotos(selectedWord: Word, default: Locale) = launch {
        val request = Pair(selectedWord, default)
        getPhotoUC.invoke(request, ::processError) {
            println("GETTTZZZ.CreateGoalVM.getPhotos ---> photos=$it")
        }
    }

}
