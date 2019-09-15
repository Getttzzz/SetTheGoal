package com.getz.setthegoal.presentationpart.feature.creategoal

import androidx.annotation.StringRes
import com.getz.setthegoal.R

enum class GoalSuggestionEnum(
    @StringRes val shortDescription: Int,
    @StringRes val fullDescription: Int
) {
    TRAVEL(R.string.travel_short, R.string.travel_full),
    CAR(R.string.travel_short, R.string.travel_full)
}