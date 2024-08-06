package com.example.weeklytodolist.data

import androidx.room.Dao
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
    fun getAllTask(): Flow<List<Task>>

}