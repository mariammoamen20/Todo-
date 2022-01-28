package com.example.todo.dao

import androidx.room.*
import androidx.room.Dao
import com.example.todo.model.Todo
import java.util.*

@Dao                                      //2-> Operations
interface TodoDao {
    @Insert
    fun insertTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("select * from Todo")
    fun getAllTodo(): List<Todo>

    @Query("select * from Todo where date = :date ")
    fun getTodoByDate(date : Date) : List<Todo>
}