package com.getz.setthegoal.core.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RestClient {

    private fun <API> constructApi(endpoint: String, clazzApi: Class<API>) =
        Retrofit.Builder()
            .client(getOkHttpClient(packInterceptors(getLoggingInterceptor())))
//            .addCallAdapterFactory() todo add coroutines
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