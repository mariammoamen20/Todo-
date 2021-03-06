package com.example.todo.dao

import androidx.room.*
import androidx.room.Dao
import com.example.todo.model.Todo

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
}