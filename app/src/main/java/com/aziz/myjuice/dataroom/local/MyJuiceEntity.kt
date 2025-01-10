package com.aziz.myjuice.dataroom.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myjuice")
data class MyJuiceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val description: String,
    val photoUrl: String,
    var isFavorite: Boolean = false,
)
