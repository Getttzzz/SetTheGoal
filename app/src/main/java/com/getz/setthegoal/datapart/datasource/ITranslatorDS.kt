package com.getz.setthegoal.datapart.datasource

interface ITranslatorDS {
    suspend fun getTranslatedInEngTextSync(rusText: String): String
}