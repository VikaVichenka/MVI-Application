package com.vikayarska.data.network.api


import com.vikayarska.domain.model.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ImageApi {

    @GET("/v1/images/search")
    suspend fun getRandomImage(
        @Header("X-Api-Key") header: String,
    ): Response<List<Image>>


}