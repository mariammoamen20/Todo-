package com.example.todo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity                                  //1-> Table
data class Todo(
    @PrimaryKey
    @ColumnInfo
    val id: Int?=null,
    @ColumnInfo
    val name: String? = null,
    @ColumnInfo
    val details: String? = null,
    @ColumnInfo
    val data: Date? = null,
    @ColumnInfo
    val is_done: Boolean? = false
)