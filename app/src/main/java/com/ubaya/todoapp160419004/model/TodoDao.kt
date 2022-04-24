package com.ubaya.todoapp160419004.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo: Todo)

    @Query("SELECT * FROM todo WHERE is_done = 0 ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    suspend fun selectTodo(id:Int): Todo

    @Delete
    suspend fun deleteTodo(todo:Todo)

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority, is_done=:is_done WHERE uuid = :id")
    suspend fun update(id: Int, title:String, notes:String, priority:Int, is_done:Int)

    @Update
    suspend fun update2(todo: Todo)
}