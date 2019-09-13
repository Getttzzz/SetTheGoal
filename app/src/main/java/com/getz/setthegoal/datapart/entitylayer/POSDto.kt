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

//todo add enum https://www.clips.uantwerpen.be/pages/mbsp-tags
//eight parts of speech: the verb (VB), the noun (NN), the pronoun (PR+DT), the adjective (JJ), the adverb (RB), the preposition (IN), the conjunction (CC), and the interjection (UH).

//CC	,conjunction, coordinating	and, or, but
//CD	,cardinal number	five, three, 13%
//DT	,determiner	the, a, these
//EX	,existential there	there were six boys
//FW	,foreign word	mais
//IN	,conjunction, subordinating or preposition	of, on, before, unless
//JJ	,adjective	nice, easy
//JJR	,adjective, comparative	nicer, easier
//JJS	,adjective, superlative	nicest, easiest
//LS	,list item marker
//MD	,verb, modal auxillary	may, should
//NN	,noun, singular or mass	tiger, chair, laughter
//NNS	,noun, plural	tigers, chairs, insects
//NNP	,noun, proper singular	Germany, God, Alice
//NNPS	,noun, proper plural	we met two Christmases ago
//PDT	,predeterminer	both his children
//POS	,possessive ending	's
//PRP	,pronoun, personal	me, you, it
//PRP$	,pronoun, possessive	my, your, our
//RB	,adverb	extremely, loudly, hard
//RBR	,adverb, comparative	better
//RBS	,adverb, superlative	best
//RP	,adverb, particle	about, off, up
//SYM	,symbol	%
//TO	,infinitival to	what to do?
//UH	,interjection	oh, oops, gosh
//VB	,verb, base form	think
//VBZ	,verb, 3rd person singular present	she thinks
//VBP	,verb, non-3rd person singular present	I think
//VBD	,verb, past tense	they thought
//VBN	,verb, past participle	a sunken ship
//VBG	,verb, gerund or present participle	thinking is fun
//WDT	,wh-determiner	which, whatever, whichever
//WP	,wh-pronoun, personal	what, who, whom
//WRB	,wh-adverb	where, when

enum class PartOfSpeech {
    CC,
    CD,
    DT,
    EX,
    FW,
    IN,
    JJ,
    JJR,
    JJS,
    LS,
    MD,
    NN,
    NNS,
    NNP,
    NNPS,
    PDT,
    POS,
    PRP,
    RB,
    RBR,
    RBS,
    RP,
    SYM,
    TO,
    UH,
    VB,
    VBZ,
    VBP,
    VBD,
    VBN,
    VBG,
    WDT,
    WP,
    WRB,
    OTHER
    ;
}


//todo add enum for russian
//Часть речи
//V глагол: работать, нравиться
//S существительное: завод, я
//A прилагательное: новый, мой, второй
//ADV наречие: плохо, отчасти
//NUM числительное: пять, 2
//PR предлог: в, между, вопреки
//CONJ союз: и, что, как
//PART частица: бы, ли, только
//INTJ междометие: ого, увы, эх
//COM композит: вице, квази, экс, ультра и другие элементы, употребляющиеся в составе сложных слов
//NID слово, представляющее собой иноязычное вкрапление в русский текст или несловесную формулу: Берлинер Цайтунг, Berliner Zeitung, Щ243
//Местоимения не рассматриваются как особая часть речи, поскольку по морфологическим (способы словоизменения) и синтаксическим свойствам они примыкают к существительным (я, кто, который), прилагательным (мой, какой) или наречиям (там, куда).
//Слова типа первый, сотый и т.д., традиционно определяемые как порядковые числительные, в корпусе считаются прилагательными.
