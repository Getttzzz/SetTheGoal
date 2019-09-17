package com.getz.setthegoal.datapart.datasource

import com.ibm.watson.language_translator.v3.LanguageTranslator
import com.ibm.watson.language_translator.v3.model.TranslateOptions
import com.ibm.watson.language_translator.v3.util.Language

class TranslatorRemoteDS(
    private val translator: LanguageTranslator
) : ITranslatorDS {
    override suspend fun getTranslatedInEngTextSync(rusText: String): String {
        val translateOptions = TranslateOptions.Builder()
            .addText(rusText)
            .source(Language.RUSSIAN)
            .target(Language.ENGLISH)
            .build()

        return try {
            val response = translator.translate(translateOptions).execute()
            if (response.statusCode == 200 && response.result != null && response.result.translations.isNotEmpty()) {
                response.result.translations[0].translationOutput
            } else {
                rusText
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            rusText
        }
    }
}