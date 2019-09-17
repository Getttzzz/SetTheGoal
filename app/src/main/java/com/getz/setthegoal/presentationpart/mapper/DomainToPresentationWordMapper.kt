package com.getz.setthegoal.presentationpart.mapper

import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Word
import com.getz.setthegoal.presentationpart.entitylayer.WordUI

class DomainToPresentationWordMapper : Gandalf<List<Word>, List<WordUI>> {
    override fun transform(source: List<Word>): List<WordUI> {
        val resultedList = arrayListOf<WordUI>()
        source.forEach { word ->
            resultedList.add(WordUI(word.originalWord, word.partOfSpeech, false))
        }
        return resultedList
    }
}