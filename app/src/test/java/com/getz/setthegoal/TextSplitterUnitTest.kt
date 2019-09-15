package com.getz.setthegoal

import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class TextSplitterUnitTest {

    @Test
    fun splitTextTest() {
        val inputText = "    first second  "
        val words = inputText.trim().split(" ")
        if (words.size >= 2) {
            Assert.assertTrue(true)
        } else {
            Assert.assertTrue(false)
        }
        println("GETTTZZZ.TextSplitterUnitTest.splitTextTest ---> size=${words.size} words=$words")
    }

    @Test
    fun randomTest() {
        val random = Random(7)
        for (index in 1..10) {
            println(
                "GETTTZZZ.TextSplitterUnitTest.randomTest ---> random.nextInt()=${random.nextInt(
                    0,
                    10
                )}"
            )
        }
    }
}