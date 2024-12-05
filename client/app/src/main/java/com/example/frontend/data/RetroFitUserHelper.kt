package com.example.frontend.data

import com.google.android.gms.common.api.Api
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetroFitWeatherHelper {
    private const val BASE_URL = "localhost"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val api: UserApi = retrofit.create(UserApi::class.java)
}