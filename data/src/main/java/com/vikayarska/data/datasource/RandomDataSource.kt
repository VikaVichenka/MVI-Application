package com.vikayarska.data.datasource

import com.vikayarska.data.BuildConfig
import com.vikayarska.data.network.api.RandomApi
import com.vikayarska.data.network.model.LoremType
import com.vikayarska.data.network.model.NameType
import com.vikayarska.data.network.model.TextType
import retrofit2.Response
import javax.inject.Inject

class RandomDataSource @Inject constructor(private val randomApi: RandomApi) {

    suspend fun getIntro(loremType: LoremType, textType: TextType, amount: Int) =
        randomApi.getIntro(BuildConfig.API_KEY_RANDOM_TEXT, loremType.value, textType.value, amount)

    suspend fun getFullNames(nameType: NameType, quantity: Int): Response<List<String>> {
        return randomApi.getFullName(
            BuildConfig.API_KEY_RANDOM_TEXT,
            nameType.value,
            quantity
        )
    }

}