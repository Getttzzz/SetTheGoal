package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.lifecycle.MutableLiveData
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.domainpart.interactorlayer.IGetPartsOfSpeechUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import kotlinx.coroutines.launch

class CreateGoalVM(
    val getPartsOfSpeechUC: IGetPartsOfSpeechUC
) : BaseVm() {

    val nextButtonSharedLD = MutableLiveData<Boolean>()
    val recognizedWordsLD = MutableLiveData<List<Word>>()

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

}
