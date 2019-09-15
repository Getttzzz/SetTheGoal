package com.getz.setthegoal

import com.getz.setthegoal.datapart.entitylayer.ResponsePOS
import com.getz.setthegoal.datapart.mapper.DataToDomainPartOfSpeechMapper
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PartOfSpeechUnitTest {

    private lateinit var mapper: DataToDomainPartOfSpeechMapper
    private lateinit var responsePOSEng: ResponsePOS
    private lateinit var responsePOSRus: ResponsePOS

    @Before
    fun firstSetup() {
        mapper = DataToDomainPartOfSpeechMapper()
        responsePOSEng = generateTestResponsePOSEnglish()
        responsePOSRus = generateTestResponsePOSRussian()
    }

    @Test
    fun mappingEngText() {
        val words = mapper.transform(responsePOSEng)
        println("GETTTZZZ.PartOfSpeechUnitTest.mappingTest ---> eng words=$words")
        Assert.assertTrue(words.isNotEmpty())
    }

    @Test
    fun mappingRusText() {
        val words = mapper.transform(responsePOSRus)
        println("GETTTZZZ.PartOfSpeechUnitTest.mappingRusText ---> rus words=$words")
        Assert.assertTrue(words.isNotEmpty())
    }


    @Test
    fun substringGetCatTest() {
        val inputString = "Cat."
        val outputString = inputString.substring(0..2)
        println("GETTTZZZ.PartOfSpeechUnitTest.substringTest ---> outputString=$outputString")
    }

    @Test
    fun substringGetCatWithUntilTest() {
        val inputString = "Cat."
        val outputString = inputString.substring(0 until 3)
        println("GETTTZZZ.PartOfSpeechUnitTest.substringTest ---> outputString=$outputString")
    }

    @Test
    fun substringGetFirstTest() {
        val inputString = "I."
        val outputString = inputString.substring(0..0)
        println("GETTTZZZ.PartOfSpeechUnitTest.substringTest ---> outputString=$outputString")
    }

    private fun generateTestResponsePOSEnglish(): ResponsePOS {
        return Gson().fromJson(
            "{\"text\":\"think, i think she thinks they thought.\",\"annotations\":{\"pos-token\":[{\"start\":0,\"end\":5,\"value\":{\"type\":\"penn\",\"tag\":\"VB\",\"characters\":[]}},{\"start\":5,\"end\":6,\"value\":{\"type\":\"penn\",\"tag\":\"COMMA\",\"characters\":[]}},{\"start\":7,\"end\":8,\"value\":{\"type\":\"penn\",\"tag\":\"OTHER\",\"characters\":[]}},{\"start\":9,\"end\":14,\"value\":{\"type\":\"penn\",\"tag\":\"VBP\",\"characters\":[]}},{\"start\":15,\"end\":18,\"value\":{\"type\":\"penn\",\"tag\":\"OTHER\",\"characters\":[]}},{\"start\":19,\"end\":25,\"value\":{\"type\":\"penn\",\"tag\":\"VBZ\",\"characters\":[]}},{\"start\":26,\"end\":30,\"value\":{\"type\":\"penn\",\"tag\":\"IN\",\"characters\":[]}},{\"start\":31,\"end\":38,\"value\":{\"type\":\"penn\",\"tag\":\"NN\",\"characters\":[]}},{\"start\":38,\"end\":39,\"value\":{\"type\":\"penn\",\"tag\":\"DOT\",\"characters\":[]}}]}}",
            ResponsePOS::class.java
        )
    }

    private fun generateTestResponsePOSRussian(): ResponsePOS {
        return Gson().fromJson(
            "{\"text\":\"Дать отличную возможность путешествовать для семьи.\",\"annotations\":{\"pos-token\":[{\"start\":0,\"end\":4,\"value\":{\"type\":\"syn-tag-rus\",\"tag\":\"V\",\"characters\":[{\"type\":\"representation\",\"tag\":\"Infinitive\"}]}},{\"start\":5,\"end\":13,\"value\":{\"type\":\"syn-tag-rus\",\"tag\":\"A\",\"characters\":[{\"type\":\"case\",\"tag\":\"Accusative\"},{\"type\":\"number\",\"tag\":\"Singular\"},{\"type\":\"gender\",\"tag\":\"Feminine\"}]}},{\"start\":14,\"end\":25,\"value\":{\"type\":\"syn-tag-rus\",\"tag\":\"S\",\"characters\":[{\"type\":\"case\",\"tag\":\"Accusative\"},{\"type\":\"animacy\",\"tag\":\"Inanimated\"},{\"type\":\"number\",\"tag\":\"Singular\"},{\"type\":\"gender\",\"tag\":\"Feminine\"}]}},{\"start\":26,\"end\":40,\"value\":{\"type\":\"syn-tag-rus\",\"tag\":\"V\",\"characters\":[{\"type\":\"representation\",\"tag\":\"Infinitive\"}]}},{\"start\":41,\"end\":44,\"value\":{\"type\":\"syn-tag-rus\",\"tag\":\"PR\",\"characters\":[]}},{\"start\":45,\"end\":50,\"value\":{\"type\":\"syn-tag-rus\",\"tag\":\"S\",\"characters\":[{\"type\":\"case\",\"tag\":\"Genitive\"},{\"type\":\"animacy\",\"tag\":\"Inanimated\"},{\"type\":\"number\",\"tag\":\"Singular\"},{\"type\":\"gender\",\"tag\":\"Feminine\"}]}},{\"start\":50,\"end\":51,\"value\":{\"type\":\"syn-tag-rus\",\"tag\":\"PUNCT\",\"characters\":[]}}]}}",
            ResponsePOS::class.java
        )
    }
}

