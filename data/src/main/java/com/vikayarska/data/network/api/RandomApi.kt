package com.vikayarska.data.network.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RandomApi {

    @GET("/api/Text/LoremIpsum")
    suspend fun getIntro(
        @Header("X-Api-Key") header: String,
        @Query("loremType") loremType: String,
        @Query("type") textType: String,
        @Query("number") amount: Int
    ): Response<String>

    @GET("/api/Name")
    suspend fun getFullName(
        @Header("X-Api-Key") header: String,
        @Query("nameType") nameType: String,
        @Query("quantity") quantity: Int
    ): Response<List<String>>
}