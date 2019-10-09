package com.getz.setthegoal.presentationpart.entitylayer

import android.content.Context
import androidx.annotation.StringRes
import com.getz.setthegoal.R

data class WhoUI(val who: String, var whoEnum: WhoEnum, var isSelected: Boolean) {
    companion object {
        fun getEnumByPosition(position: Int) = when (position) {
            0 -> WhoEnum.FAMILY
            1 -> WhoEnum.MYSELF
            else -> throw IllegalStateException("There's no enum val by your position.")
        }

        fun generateForWhom(context: Context): List<WhoUI> {
            val whoList = arrayListOf<WhoUI>()
            WhoEnum.values().forEach { enum ->
                whoList.add(
                    WhoUI(
                        who = context.getString(enum.strRes),
                        whoEnum = enum,
                        isSelected = false
                    )
                )
            }
            return whoList
        }
    }
}

enum class WhoEnum(val personName: String, @StringRes val strRes: Int) {
    FAMILY("family", R.string.for_family),
    MYSELF("myself", R.string.for_myself);

    companion object {
        fun getEnumByWho(who: String) = when (who) {
            FAMILY.personName -> FAMILY
            MYSELF.personName -> MYSELF
            else -> throw IllegalStateException("There's no enum object by this query.")
        }
    }
}