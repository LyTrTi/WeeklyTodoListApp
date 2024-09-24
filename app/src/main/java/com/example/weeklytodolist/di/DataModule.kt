package com.example.weeklytodolist.di

import android.content.Context
import androidx.room.Room
import com.example.weeklytodolist.data.firebase.firestore.FirestoreTaskRepository
import com.example.weeklytodolist.data.localData.OfflineTaskRepository
import com.example.weeklytodolist.data.localData.TaskDatabase
import com.example.weeklytodolist.domain.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: TaskDatabase) = database.getDao()

    @Singleton
    @Provides
    @Named("offlineTaskRepository")
    fun provideOfflineTaskRepository(database: TaskDatabase): TaskRepository {
        return OfflineTaskRepository(database.getDao())
    }

    @Singleton
    @Provides
    @Named("firestoreTaskRepository")
    fun provideFirestoreTaskRepository(
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore
    ): TaskRepository {
        return FirestoreTaskRepository(fireStore, auth)
    }
}