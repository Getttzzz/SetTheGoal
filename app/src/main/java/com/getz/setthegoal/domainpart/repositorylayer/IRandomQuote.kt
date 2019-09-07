package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.entitylayer.Quote

interface IRandomQuote {
    fun getRandomQuote(): Quote
}