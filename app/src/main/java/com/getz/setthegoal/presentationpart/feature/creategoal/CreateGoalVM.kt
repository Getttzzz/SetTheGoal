package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetPhotoUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.PhotoUI
import com.getz.setthegoal.presentationpart.entitylayer.WordUI
import kotlinx.coroutines.launch
import java.util.Locale

class CreateGoalVM(
    private val getPartsOfSpeechUC: IGetPartsOfSpeechUC,
    private val getPhotoUC: IGetPhotoUC,
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

    lateinit var writtenGoalText: String
    lateinit var selectedImageUrl: PhotoUI
    lateinit var selectedDeadline: String
    lateinit var selectedSubTasks: ArrayList<String>

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

}
