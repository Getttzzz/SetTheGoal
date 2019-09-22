package com.getz.setthegoal

import org.junit.Test

class RandomThreeDigitsUnitTest {

    @Test
    fun generateRandomThreeDigitsBasedOnTime() {
        val currentTimeMillis = System.currentTimeMillis()
        val lastThreeDigits = currentTimeMillis % 1000
        println("GETTTZZZ.RandomThreeDigitsUnitTest.generateRandomThreeDigitsBasedOnTime ---> currentTimeMillis=$currentTimeMillis lastThreeDigits=$lastThreeDigits")
    }
}