package com.getz.setthegoal.datapart.mapper

import com.getz.setthegoal.datapart.entitylayer.PartOfSpeechEnum
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Word

class DataToDomainPartOfSpeechFilter : Gandalf<List<Word>, List<Word>> {

    override fun transform(source: List<Word>): List<Word> =
        source.filter {
            when (it.partOfSpeech) {
                PartOfSpeechEnum.VERB -> true
                PartOfSpeechEnum.NOUN -> true
                PartOfSpeechEnum.ADJECTIVE -> true
                PartOfSpeechEnum.ADVERB -> true
                PartOfSpeechEnum.OTHER -> false
            }
        }
            .sortedWith(Comparator { o1, o2 ->
                //1.VERB
                //2.NOUN
                //3.ADJECTIVE
                //4.ADVERB
                when {
                    o1.partOfSpeech == PartOfSpeechEnum.VERB && o2.partOfSpeech == PartOfSpeechEnum.NOUN -> -1
                    o1.partOfSpeech == PartOfSpeechEnum.VERB && o2.partOfSpeech == PartOfSpeechEnum.ADJECTIVE -> -1
                    o1.partOfSpeech == PartOfSpeechEnum.VERB && o2.partOfSpeech == PartOfSpeechEnum.ADVERB -> -1
                    o1.partOfSpeech == PartOfSpeechEnum.VERB && o2.partOfSpeech == PartOfSpeechEnum.VERB -> 0

                    o1.partOfSpeech == PartOfSpeechEnum.NOUN && o2.partOfSpeech == PartOfSpeechEnum.VERB -> 1
                    o1.partOfSpeech == PartOfSpeechEnum.NOUN && o2.partOfSpeech == PartOfSpeechEnum.NOUN -> 0
                    o1.partOfSpeech == PartOfSpeechEnum.NOUN && o2.partOfSpeech == PartOfSpeechEnum.ADJECTIVE -> -1
                    o1.partOfSpeech == PartOfSpeechEnum.NOUN && o2.partOfSpeech == PartOfSpeechEnum.ADVERB -> -1

                    o1.partOfSpeech == PartOfSpeechEnum.ADJECTIVE && o2.partOfSpeech == PartOfSpeechEnum.VERB -> 1
                    o1.partOfSpeech == PartOfSpeechEnum.ADJECTIVE && o2.partOfSpeech == PartOfSpeechEnum.NOUN -> 1
                    o1.partOfSpeech == PartOfSpeechEnum.ADJECTIVE && o2.partOfSpeech == PartOfSpeechEnum.ADJECTIVE -> 0
                    o1.partOfSpeech == PartOfSpeechEnum.ADJECTIVE && o2.partOfSpeech == PartOfSpeechEnum.ADVERB -> -1

                    o1.partOfSpeech == PartOfSpeechEnum.ADVERB && o2.partOfSpeech == PartOfSpeechEnum.VERB -> 1
                    o1.partOfSpeech == PartOfSpeechEnum.ADVERB && o2.partOfSpeech == PartOfSpeechEnum.NOUN -> 1
                    o1.partOfSpeech == PartOfSpeechEnum.ADVERB && o2.partOfSpeech == PartOfSpeechEnum.ADJECTIVE -> 1
                    o1.partOfSpeech == PartOfSpeechEnum.ADVERB && o2.partOfSpeech == PartOfSpeechEnum.ADVERB -> 0
                    else -> 0
                }
            })
}