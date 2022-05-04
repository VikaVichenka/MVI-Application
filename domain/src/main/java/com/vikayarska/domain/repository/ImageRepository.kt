package com.vikayarska.domain.repository

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.Image

interface ImageRepository {
    suspend fun getImage(): BaseResult<Image>
}