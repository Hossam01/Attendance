package com.weightwatchers.ww_exercise_01.api

import android.os.Build
import com.google.gson.GsonBuilder
import com.weightwatchers.ww_exercise_01.api.base.BaseClientApi
import com.john.attendance.conf.SharedCodeConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit

class ClientAPI : BaseClientApi() {

    private val baseURL: String
        get() {
            return SharedCodeConfiguration.url
        }

    private val client: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl(this.baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(this.okHttp)
                .build()
        }

    private val okHttp: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.readTimeout(
                SharedCodeConfiguration.requestTimeoutMillis,
                TimeUnit.MILLISECONDS
            )
            builder.writeTimeout(
                SharedCodeConfiguration.requestTimeoutMillis,
                TimeUnit.MILLISECONDS
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.connectTimeout(Duration.ofMillis(SharedCodeConfiguration.connectTimeoutMillis))
            } else {
                builder.connectTimeout(
                    SharedCodeConfiguration.connectTimeoutMillis,
                    TimeUnit.MILLISECONDS
                )
            }

            return builder.build()
        }
}