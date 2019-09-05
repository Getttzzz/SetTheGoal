package com.getz.setthegoal.core.network

interface RandomQuoteApi {

    //https://api.forismatic.com/api/1.0/?method=getQuote&format=jsonp&jsonp=parseQuote&lang=en

    fun getQuote()
}