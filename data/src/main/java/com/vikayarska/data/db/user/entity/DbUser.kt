package com.vikayarska.data.db.user.entity

import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity(
    tableName = "users", indices = [Index(
        value = ["first_name", "last_name"],
        unique = true
    )]
)
data class DbUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val birth: Date,
    var intro: String,
    @ColumnInfo(name = "image_id") var imageId: String,
    @ColumnInfo(name = "image_url") var imageUrl: String
) : Serializable

data class UserMinimal(
    val id: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String
)
