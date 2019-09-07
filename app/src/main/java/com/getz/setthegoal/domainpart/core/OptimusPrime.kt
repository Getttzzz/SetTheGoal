package com.getz.setthegoal.domainpart.core

interface OptimusPrime<in Source, out Result> {
    fun transform(source: Source): Result
}