package com.example.androidjannes.network

import com.example.androidjannes.NbaSeasonsState
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api-nba-v1.p.rapidapi.com"
private const val API_KEY = "8d607be43dmsh910c31a4dcf6bf0p12017ejsnb0b834ab0475"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", "api-nba-v1.p.rapidapi.com")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    )
    .build()

interface NbaApiService{
    @GET("seasons")
    suspend fun getSeasons() : NbaSeasonsResponse

    @GET("standings")
    suspend fun getStandings(
        @Query("season") season : Int = 2021,
        @Query("league") league : String = "standard"
    ) : NbaStandingResponse
}

object NbaApi{
    val retrofitService : NbaApiService by lazy {
        retrofit.create(NbaApiService::class.java)
    }
}