package com.getz.setthegoal.presentationpart.entitylayer

import android.content.Context
import androidx.annotation.StringRes
import com.getz.setthegoal.R

data class DeadlineUI(val text: String, var deadlineEnum: DeadlineEnum, var isSelected: Boolean) {
    companion object {
        fun getEnumByPosition(position: Int) = when (position) {
            0 -> DeadlineEnum.ONE_WEEK
            1 -> DeadlineEnum.ONE_MONTH
            2 -> DeadlineEnum.THREE_MONTHS
            3 -> DeadlineEnum.SIX_MONTHS
            4 -> DeadlineEnum.ONE_YEAR
            else -> throw IllegalStateException("There's no enum val by your position, which is $position")
        }

        fun generateDeadlines(context: Context): List<DeadlineUI> {
            val deadlines = arrayListOf<DeadlineUI>()
            DeadlineEnum.values().forEach { enum ->
                deadlines.add(
                    DeadlineUI(
                        text = context.getString(enum.strRes),
                        deadlineEnum = enum,
                        isSelected = false
                    )
                )
            }
            return deadlines
        }
    }
}

enum class DeadlineEnum(val timeRange: String, @StringRes val strRes: Int) {
    ONE_WEEK("one_week", R.string.one_week),
    ONE_MONTH("one_month", R.string.one_month),
    THREE_MONTHS("three_months", R.string.three_months),
    SIX_MONTHS("six_months", R.string.six_months),
    ONE_YEAR("one_year", R.string.one_year);

    companion object {
        fun getStrResByTimeRange(timeRange: String) = when (timeRange) {
            ONE_WEEK.timeRange -> ONE_WEEK
            ONE_MONTH.timeRange -> ONE_MONTH
            THREE_MONTHS.timeRange -> THREE_MONTHS
            SIX_MONTHS.timeRange -> SIX_MONTHS
            ONE_YEAR.timeRange -> ONE_YEAR
            else -> throw IllegalStateException("There's no enum object by this time range, which is $timeRange")
        }
    }
}
