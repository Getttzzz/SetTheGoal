package com.getz.setthegoal.datapart.entitylayer

import com.google.gson.annotations.SerializedName

data class QuoteDto(
    @SerializedName("quoteText") val quoteText: String?,
    @SerializedName("quoteAuthor") val quoteAuthor: String?,
    @SerializedName("quoteLink") val quoteLink: String?
)