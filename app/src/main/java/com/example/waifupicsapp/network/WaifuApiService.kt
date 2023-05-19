package com.example.waifupicsapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.waifu.pics/sfw/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WaifuApiService {
    @GET("waifu")
    suspend fun getWaifuPic(): WaifuPic
}

object WaifuApi {
    val retrofitService: WaifuApiService by lazy {
        retrofit.create(WaifuApiService::class.java)
    }
}