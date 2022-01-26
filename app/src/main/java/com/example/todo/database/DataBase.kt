package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.DateConverter
import com.example.todo.dao.TodoDao
import com.example.todo.model.Todo

@Database(entities = [Todo::class], version = 1)     //3-> DataBase
@TypeConverters(DateConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private val DATABASE_NAME = "todo_database"
        private var my_data_base: DataBase? = null

        fun getInstance(context: Context): DataBase {
            if (my_data_base == null) {
                my_data_base = Room.databaseBuilder(
                    context, DataBase::class.java, DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
            }
            return my_data_base!!
        }
    }
}