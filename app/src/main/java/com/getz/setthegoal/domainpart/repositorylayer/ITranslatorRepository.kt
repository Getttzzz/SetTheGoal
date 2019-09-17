package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.core.IRepository

interface ITranslatorRepository : IRepository {
    suspend fun translateRusToEng(inputRusText: String): String
}