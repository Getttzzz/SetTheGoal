package com.getz.setthegoal.datapart.mapper

import com.getz.setthegoal.datapart.entitylayer.AbbreviationTypeEnum
import com.getz.setthegoal.datapart.entitylayer.PartOfSpeechEnum
import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.entitylayer.WordObj
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Word

class DataToDomainPartOfSpeechMapper : Gandalf<ResponsePOS, List<Word>> {

    override fun transform(source: ResponsePOS): List<Word> {
        val words = source.annotations?.words
        if (words == null || words.isEmpty()) return emptyList()

        /**
         * If texterra api returns first word as type "PENN",
         * that means it is english text. Otherwise, russian.
         * */
        val firstWord = words[0]

        return if (firstWord.value.type == AbbreviationTypeEnum.PENN_TREEBANK.abrType) {
            parseEnglishText(words, source.text)
        } else {
            parseRussianText(words, source.text)
        }
    }

    private fun parseEnglishText(words: List<WordObj>, inputText: String): ArrayList<Word> {
        val resultedWords = arrayListOf<Word>()

        words.forEach { wordObj ->
            val extractedWord = inputText.substring(wordObj.start until wordObj.end)

            /**
             * I make substring to cut an ending of the part of speech
             * The ending (VBZ, VBP, VBD) describes details about part of speech.
             * However, for me it doesn't matter. I need only base part of speech.
             *
             * Input String: VBZ (3rd person singular present verb "thinks")
             * Output String: VB (Just verb, it's only what I need)
             * */
            val baseAbbreviation = wordObj.value.tag.substring(0..1)

            val partOfSpeechEnum = when (baseAbbreviation) {
                "NN" -> PartOfSpeechEnum.NOUN
                "VB" -> PartOfSpeechEnum.VERB
                "JJ" -> PartOfSpeechEnum.ADJECTIVE
                "RB" -> PartOfSpeechEnum.ADVERB
                else -> PartOfSpeechEnum.OTHER
            }

            resultedWords.add(
                Word(
                    originalWord = extractedWord,
                    partOfSpeech = partOfSpeechEnum
                )
            )
        }

        return resultedWords
    }

    private fun parseRussianText(words: List<WordObj>, inputText: String): ArrayList<Word> {
        val resultedWords = arrayListOf<Word>()

        words.forEach { wordObj ->
            val extractedWord = inputText.substring(wordObj.start until wordObj.end)
            val partOfSpeechEnum = when (wordObj.value.tag) {
                "S" -> PartOfSpeechEnum.NOUN
                "V" -> PartOfSpeechEnum.VERB
                "A" -> PartOfSpeechEnum.ADJECTIVE
                "ADV" -> PartOfSpeechEnum.ADVERB
                else -> PartOfSpeechEnum.OTHER
            }
            resultedWords.add(
                Word(
                    originalWord = extractedWord,
                    partOfSpeech = partOfSpeechEnum
                )
            )
        }

        return resultedWords
    }
}