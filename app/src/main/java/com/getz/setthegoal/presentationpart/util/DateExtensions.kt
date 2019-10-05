package com.getz.setthegoal.presentationpart.util

import com.getz.setthegoal.presentationpart.entitylayer.DeadlineEnum
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.concurrent.TimeUnit

fun Date.getDaysIn(enum: DeadlineEnum) = when (enum) {
    DeadlineEnum.ONE_WEEK -> this.getDaysInCalendarValue(Calendar.DAY_OF_YEAR, 7)
    DeadlineEnum.ONE_MONTH -> this.getDaysInCalendarValue(Calendar.MONTH, 1)
    DeadlineEnum.THREE_MONTHS -> this.getDaysInCalendarValue(Calendar.MONTH, 3)
    DeadlineEnum.SIX_MONTHS -> this.getDaysInCalendarValue(Calendar.MONTH, 6)
    DeadlineEnum.ONE_YEAR -> this.getDaysInCalendarValue(Calendar.YEAR, 1)
}

private fun Date.getDaysInCalendarValue(calendarField: Int, amount: Int): Int {
    val todayCalendar = GregorianCalendar.getInstance()
    val target = GregorianCalendar.getInstance().apply {
        time = this@getDaysInCalendarValue

        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 0)

        add(calendarField, amount)
    }

    val remainingDays =
        TimeUnit.MILLISECONDS.toDays(target.timeInMillis - todayCalendar.timeInMillis)
    return remainingDays.toInt()
}


