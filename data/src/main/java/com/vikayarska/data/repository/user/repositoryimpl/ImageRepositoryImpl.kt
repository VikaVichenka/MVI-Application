package com.vikayarska.data.repository.user.repositoryimpl

import com.vikayarska.data.network.getResponse
import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.repository.ImageRepository
import com.vikayarska.domain.model.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(private val api: com.vikayarska.data.datasource.ImageDataSource) :
    ImageRepository {
    override suspend fun getImage(): BaseResult<Image> {
        return when (val response = api.getImage().getResponse()) {
            is BaseResult.Success -> BaseResult.Success(response.data.first())
            is BaseResult.Empty -> BaseResult.Empty()
            is BaseResult.Error -> BaseResult.Error(response.message, response.exception)
        }
    }
}