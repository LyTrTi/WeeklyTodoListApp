package com.example.weeklytodolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("""
        select *
        from task
    """)
    fun getAllTaskFlow(): Flow<List<Task>>

    @Query("""
        select *
        from task
        where id = :taskId  
    """)
    fun getTaskByIdFlow(taskId: Int): Flow<Task>

    @Query("""
        select *
        from task
        order by id asc
    """)
    suspend fun getAllTask(): List<Task>

    @Query("""
        select *
        from task
        where id = :taskId  
    """)
    suspend fun getTaskById(taskId: Int): Task

    @Query("""
        select *
        from task
        where name like :taskName
    """)
    suspend fun searchByName(taskName: String): List<Task>

    @Upsert
    suspend fun modifyTable(task: Task)

    @Query("""
        delete from task where id = :taskId
    """)
    suspend fun deleteTask(taskId: Int)
}