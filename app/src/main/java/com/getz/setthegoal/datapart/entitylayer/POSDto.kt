package com.getz.setthegoal.datapart.entitylayer

import com.google.gson.annotations.SerializedName

/**
 * Request body
 *  [ {
 *  "text" : "John likes the blue house at the end of the street."
 *  }]
 *
 *
 * Response
 *  [
 *      {
 *      "text": "John likes the blue house at the end of the street.",
 *      "annotations": {
 *      "pos-token": [
 *          {
 *              "start": 0,
 *              "end": 4,
 *              "value": {
 *                  "type": "penn",
 *                  "tag": "NNP",
 *                  "characters": [
 *                      {
 *                          "type": "case",
 *                          "tag": "Nominative"
 *                          },...
 *                      ]
 *          }
 *      },...
 */


data class RequestPOS(val texts: List<TextObj>)

data class TextObj(@SerializedName("text") val text: String)

data class ResponsePOS(
    @SerializedName("text") val text: String?,
    @SerializedName("annotations") val annotations: AnnotationsObj?
)

data class AnnotationsObj(
    @SerializedName("pos-token") val words: List<Word>?
)

data class Word(
    @SerializedName("start") val start: Int?,
    @SerializedName("end") val end: Int?,
    @SerializedName("value") val value: ValueObj?
)

data class ValueObj(
    @SerializedName("type") val type: String?,
    @SerializedName("tag") val tag: String?
//    @SerializedName("characters") characters:List<>
)

/**
 * English contains eight parts of speech:
 *
 * 1.the verb (VB) (rus glagol, q:chto delat?)
 * 2.the noun (NN)  (rus sushestvitelnoe, q:kto?)
 * 3.the pronoun (PR+DT) (rus mestoimenie, q:kto?)
 * 4.the adjective (JJ) (rus prilagatelnoe, q:kakoy?)
 * 5.the adverb (RB) (rus narechie, q:gde? kogda? pochemy? kak?)
 * 6.the preposition (IN) (rus predlog, related to noun)
 * 7.the conjunction (CC) (rus soyuz)
 * 8.the interjection (UH) (rus mezhdometiya)
 *
 * Source https://www.clips.uantwerpen.be/pages/mbsp-tags
 * */

/**
 * Tag set by Penn Treebank
 *
 * 1.1 VB	verb, base form	think (Api says it's VB)
 * 1.2 VBZ	verb, 3rd person singular present	she thinks (Api says it's VBZ)
 * 1.3 VBP	verb, non-3rd person singular present	I think (Api says it's VBP)
 * 1.4 VBD	verb, past tense	they thought (Api says it's VBD)
 * 1.5 VBG	verb, gerund or present participle	thinking is fun (Api says it's NN)***
 * 2.1 NN	noun, singular or mass	tiger, chair, laughter
 * 2.2 NNS	noun, plural	tigers, chairs, insects
 * 2.3 NNP	noun, proper singular	Germany, God, Alice
 * 3.1 PRP	pronoun, personal	me, you, it (Api says it's OTHER)***
 * 3.2 PRP$	pronoun, possessive	my, your, our (Api says it's OTHER)***
 * 4.1 JJ	adjective	nice, easy (Api says it's JJ)
 * 4.2 JJR	adjective, comparative	nicer, easier (Api says it's JJ)***
 * 4.3 JJS	adjective, superlative	nicest, easiest (Api says it's JJS)
 * 5.1 RB	adverb,	extremely, loudly, hard (Api says it's RB)
 * 5.2 RBR	adverb, comparative	better (Api says it's RB)***
 * 6.  IN	preposition	of, on, unless (Api says it's IN)
 * 7.  CC	conjunction, coordinating	and, or, but
 * 8.  UH	interjection	oh, oops, gosh (Api doesn't support it)***
 * DT	determiner	the, a, these (Api says it's DT)
 * WDT	wh-determiner	which, whatever, whichever (Api says it's WDT)
 */


enum class PartOfSpeechEng {
    VB,
    NN,
    JJ,
    RB
    ;
}

/**
 * 1. V     глагол: работать, нравиться
 * 2. S     существительное: завод, я
 * 3. A     прилагательное: новый, мой, второй
 * 4. ADV   наречие: плохо, отчасти
 * 5. NUM   числительное: пять, 2
 * 6. PR    предлог: в, между, вопреки
 * 7. CONJ  союз: и, что, как
 * 8. PART  частица: бы, ли, только
 * 9. INTJ  междометие: ого, увы, эх
 * */

enum class PartOfSpeechRus {
    V,
    S,
    A,
    ADV,
    NUM,
    PR,
    CONJ,
    PART,
    INTJ,
    COM
    ;
}


