package com.getz.setthegoal.dependencyinjection

import com.getz.setthegoal.datapart.api.RandomQuoteApi
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = Kodein.Module(ModulesNames.NETWORK_MODULE) {
    bind<RestClient>() with singleton {
        RestClient()
    }
}

class RestClient {

    var randomQuoteApi: RandomQuoteApi

    init {
        randomQuoteApi = constructApi(RANDOM_QUOTE_BASE_URL, RandomQuoteApi::class.java)
    }

    companion object {
        const val RANDOM_QUOTE_BASE_URL = "https://api.forismatic.com/api/1.0/"
    }

    private fun <API> constructApi(endpoint: String, clazzApi: Class<API>) =
        Retrofit.Builder()
            .client(getOkHttpClient(packInterceptors(getLoggingInterceptor())))
            //todo add GSONPConverterFactory for a random quote request
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .baseUrl(endpoint)
            .build()
            .create(clazzApi)

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

}