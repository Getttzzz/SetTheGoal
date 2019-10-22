package com.getz.setthegoal.presentationpart.feature.welcome

import androidx.annotation.StringRes
import com.getz.setthegoal.R
import kotlin.random.Random

enum class OkayEnum(@StringRes val strRes: Int) {
    YEAH(R.string.yeah),                //Ага
    GOOD(R.string.good),                //Хорошо
    ABSOLUTELY(R.string.absolutely),    //Безусловно
    AGREED(R.string.agreed),            //Согласен
    ALL_RIGHT(R.string.all_right),      //Все верно
    EXACTLY(R.string.exactly),          //Точно
    YEP(R.string.yep),                  //Угу
    SURE(R.string.sure),                //Естественно
    OF_COURSE(R.string.of_course);      //Разумеется

    companion object {
        fun getRandomAgreement(): OkayEnum {
            val currentTimeMillis = System.currentTimeMillis()
            val lastThreeDigits = currentTimeMillis % 1000
            val random = Random(lastThreeDigits)
            return values()[random.nextInt(values().size)]
        }
    }
}