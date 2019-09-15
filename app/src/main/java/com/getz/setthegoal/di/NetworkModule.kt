package com.getz.setthegoal.di

import com.getz.setthegoal.datapart.api.RandomQuoteApi
import com.getz.setthegoal.datapart.api.TexterraApi
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val RANDOM_QUOTE_BASE_URL = "https://api.forismatic.com/api/1.0/"
const val TEXTERRA_BASE_URL = "https://api.ispras.ru/texterra/v1/"

const val TEXTERRA_API_TAG = "TEXTERRA_API_TAG"
const val FORISMATIC_API_TAG = "FORISMATIC_API_TAG"
const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"

val networkModule = Kodein.Module(ModulesNames.NETWORK_MODULE) {
    import(interceptorModule)

    bind<OkHttpClient>() with singleton {
        getOkHttpClient(packInterceptors(instance(tag = LOGGING_INTERCEPTOR)))
    }

    bind<Retrofit>(tag = FORISMATIC_API_TAG) with singleton {
        getRetrofit(
            RANDOM_QUOTE_BASE_URL,
            instance()
        )
    }
    bind<RandomQuoteApi>() with singleton { createApi<RandomQuoteApi>(instance(tag = FORISMATIC_API_TAG)) }

    bind<Retrofit>(tag = TEXTERRA_API_TAG) with singleton {
        getRetrofit(
            TEXTERRA_BASE_URL,
            instance()
        )
    }
    bind<TexterraApi>() with singleton { createApi<TexterraApi>(instance(tag = TEXTERRA_API_TAG)) }
}

private val interceptorModule = Kodein.Module(ModulesNames.INTERCEPTOR_MODULE) {
    bind<Interceptor>(tag = LOGGING_INTERCEPTOR) with singleton { getLoggingInterceptor() }
}

private fun getRetrofit(endpoint: String, client: OkHttpClient) =
    Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .baseUrl(endpoint)
        .build()

private fun getLoggingInterceptor() =
    HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

private fun getGson() =
    GsonBuilder().setLenient().create()

private fun getOkHttpClient(interceptors: List<Interceptor>) =
    OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .also {
            for (interceptor in interceptors) {
                it.addInterceptor(interceptor)
            }
        }
        .build()

private fun packInterceptors(vararg interceptors: Interceptor) = listOf(*interceptors)

private inline fun <reified API> createApi(retrofit: Retrofit) =
    retrofit.create(API::class.java) as API