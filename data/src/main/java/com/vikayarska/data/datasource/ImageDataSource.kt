package com.vikayarska.data.datasource

import com.vikayarska.data.BuildConfig
import com.vikayarska.data.network.api.ImageApi
import javax.inject.Inject

class ImageDataSource @Inject constructor(private val imageApi: ImageApi) {

    suspend fun getImage() =
        imageApi.getRandomImage(BuildConfig.API_KEY_CAT_IMAGES)
}