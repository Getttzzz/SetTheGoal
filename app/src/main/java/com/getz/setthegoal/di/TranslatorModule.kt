package com.getz.setthegoal.di

import com.getz.setthegoal.BuildConfig
import com.ibm.cloud.sdk.core.service.security.IamOptions
import com.ibm.watson.language_translator.v3.LanguageTranslator
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val translatorModule = Kodein.Module(ModulesNames.TRANSLATOR_MODULE) {
    bind<LanguageTranslator>() with singleton {
        val iamOptions = IamOptions.Builder()
            .apiKey(BuildConfig.IBMTranslatorApiKey)
            .build()
        val translator = LanguageTranslator("2018-05-01", iamOptions)
            .apply { endPoint = "https://gateway-lon.watsonplatform.net/language-translator/api" }
        translator
    }
}