package com.getz.setthegoal

import com.getz.setthegoal.presentationpart.entitylayer.DeadlineEnum
import com.getz.setthegoal.presentationpart.util.getDaysIn
import org.junit.Test
import java.util.Date

class DaysDifferenceUnitTest {

    @Test
    fun getDaysDifference() {
//        val createdAt = Date().apply { time = 1570222740000 } //yesterday   23:59
        val createdAt = Date().apply { time = 1570222860000 } //today       00:01
        val daysIn = createdAt.getDaysIn(DeadlineEnum.ONE_WEEK)
        println("GETTTZZZ.DaysDifferenceUnitTest.getDaysDifference ---> daysIn=$daysIn")
    }
}