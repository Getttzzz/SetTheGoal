package com.getz.setthegoal.presentationpart.entitylayer

import androidx.annotation.StringRes
import com.getz.setthegoal.R

data class WorryUI(var worryEnum: WorryEnum, var isSelected: Boolean) {
    companion object {
        fun getEnumByPosition(deadlineTimeRange: String, position: Int): WorryEnum {
            val exception = IllegalStateException("There's no enum val by position.")
            return when (deadlineTimeRange) {
                DeadlineEnum.ONE_WEEK.timeRange -> {
                    when (position) {
                        0 -> WorryEnum.ONCE_A_DAY
                        1 -> WorryEnum.ONCE_EVERY_THREE_DAYS
                        else -> throw exception
                    }
                }
                DeadlineEnum.ONE_MONTH.timeRange -> {
                    when (position) {
                        0 -> WorryEnum.ONCE_EVERY_THREE_DAYS
                        1 -> WorryEnum.ONCE_EVERY_WEEK
                        else -> throw exception
                    }
                }
                DeadlineEnum.THREE_MONTHS.timeRange -> {
                    when (position) {
                        0 -> WorryEnum.ONCE_EVERY_WEEK
                        1 -> WorryEnum.ONCE_EVERY_MONTH
                        else -> throw exception
                    }
                }
                DeadlineEnum.SIX_MONTHS.timeRange -> {
                    when (position) {
                        0 -> WorryEnum.ONCE_EVERY_MONTH
                        1 -> WorryEnum.ONCE_EVERY_TWO_MONTHS
                        else -> throw exception
                    }
                }
                DeadlineEnum.ONE_YEAR.timeRange -> {
                    when (position) {
                        0 -> WorryEnum.ONCE_EVERY_MONTH
                        1 -> WorryEnum.ONCE_EVERY_TWO_MONTHS
                        else -> throw exception
                    }
                }
                else -> throw IllegalStateException("There's no enum val by deadlineTimeRange.")
            }
        }

        fun generateWorries(deadlineTimeRange: String): List<WorryUI> {
            val worries = arrayListOf<WorryUI>()
            when (deadlineTimeRange) {
                DeadlineEnum.ONE_WEEK.timeRange -> {
                    worries.add(WorryUI(WorryEnum.ONCE_A_DAY, false))
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_THREE_DAYS, false))
                }
                DeadlineEnum.ONE_MONTH.timeRange -> {
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_THREE_DAYS, false))
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_WEEK, false))
                }
                DeadlineEnum.THREE_MONTHS.timeRange -> {
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_WEEK, false))
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_MONTH, false))
                }
                DeadlineEnum.SIX_MONTHS.timeRange -> {
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_MONTH, false))
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_TWO_MONTHS, false))
                }
                DeadlineEnum.ONE_YEAR.timeRange -> {
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_MONTH, false))
                    worries.add(WorryUI(WorryEnum.ONCE_EVERY_TWO_MONTHS, false))
                }
            }

            return worries
        }
    }
}

/**
 * One week -> ONCE_A_DAY or ONCE_EVERY_THREE_DAYS (2 or 7 notifs)
 * One month -> ONCE_EVERY_THREE_DAYS or ONCE_EVERY_WEEK (4 or 10 notifs)
 * 3 month -> ONCE_EVERY_WEEK or ONCE_EVERY_MONTH (3 or 12 notifs)
 * 6 month -> ONCE_EVERY_MONTH or ONCE_EVERY_TWO_MONTHS (3 or 6 notifs)
 * 1 year -> ONCE_EVERY_MONTH or ONCE_EVERY_TWO_MONTHS (6 or 12 notifs)
 * */

enum class WorryEnum(val worryName: String, @StringRes val strRes: Int) {
    ONCE_A_DAY("once_a_day", R.string.once_a_day),
    ONCE_EVERY_THREE_DAYS("once_every_three_days", R.string.once_every_three_days),
    ONCE_EVERY_WEEK("once_every_week", R.string.once_every_week),
    ONCE_EVERY_MONTH("once_every_month", R.string.once_every_month),
    ONCE_EVERY_TWO_MONTHS("once_every_two_months", R.string.once_every_two_months);

    companion object {
        fun getEnumByWorryName(worryText: String) = when (worryText) {
            ONCE_A_DAY.worryName -> ONCE_A_DAY
            ONCE_EVERY_THREE_DAYS.worryName -> ONCE_EVERY_THREE_DAYS
            ONCE_EVERY_WEEK.worryName -> ONCE_EVERY_WEEK
            ONCE_EVERY_MONTH.worryName -> ONCE_EVERY_MONTH
            ONCE_EVERY_TWO_MONTHS.worryName -> ONCE_EVERY_TWO_MONTHS
            else -> throw IllegalStateException("There's no enum object by this query.")
        }
    }
}