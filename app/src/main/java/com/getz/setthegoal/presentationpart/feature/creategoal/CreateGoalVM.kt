package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPhotoUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.WordUI
import kotlinx.coroutines.launch
import java.util.Locale

class CreateGoalVM(
    private val getPartsOfSpeechUC: IGetPartsOfSpeechUC,
    private val getPhotoUC: IGetPhotoUC,
    private val gandalfMapper: Gandalf<List<Word>, List<WordUI>>
) : BaseVm() {

    val nextButtonSharedLD = MutableLiveData<Boolean>()
    val pressNextSharedLD = MutableLiveData<Unit>()
    val recognizedWordsLD = MutableLiveData<List<WordUI>>()
    val keyboardListenerLD = MutableLiveData<Boolean>()
    val photosResultLD = MutableLiveData<List<Photo>>()

    var isForFamily = false

    lateinit var writtenGoalText: String
    lateinit var selectedImageUrl: String
    lateinit var selectedDeadline: String
    lateinit var selectedSubTasks: ArrayList<String>

    fun recognizePartsOfSpeech() = launch {
        getPartsOfSpeechUC.invoke(writtenGoalText, ::processError) { recognizedWords ->

            recognizedWordsLD.value = gandalfMapper.transform(recognizedWords)
        }
    }

    fun getPhotos(selectedWord: String, default: Locale) = launch {
        val request = Pair(selectedWord, default)
        getPhotoUC.invoke(request, ::processError) { photos ->
            println("GETTTZZZ.CreateGoalVM.getPhotos ---> photos=$photos")
            photosResultLD.value = photos
        }
    }

}
