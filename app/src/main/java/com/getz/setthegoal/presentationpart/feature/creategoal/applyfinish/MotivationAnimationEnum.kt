package com.getz.setthegoal.presentationpart.feature.creategoal.applyfinish

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.getz.setthegoal.R
import kotlin.random.Random

enum class MotivationAnimationEnum(@RawRes val rawRes: Int, @StringRes val strRes: Int) {
    BICYCLE(R.raw.bicycle, R.string.mae_bicycle),
    MEDITATION(R.raw.meditate_peace_and_love, R.string.mae_meditation),
    FRUITS(R.raw.bouncing_fruits, R.string.mae_fruits),
    COFFEE(R.raw.coffee, R.string.mae_coffee),
    MUSIC(R.raw.guitar, R.string.mae_music),
    CROISSANT(R.raw.croissant, R.string.mae_croissant),
    BEAR(R.raw.bear, R.string.mae_bear)
    ;

    companion object {
        fun getRandomEnum(): MotivationAnimationEnum {
            val currentTimeMillis = System.currentTimeMillis()
            val lastThreeDigits = currentTimeMillis % 1000
            val random = Random(lastThreeDigits)
            return values()[random.nextInt(values().size)]
        }
    }
}