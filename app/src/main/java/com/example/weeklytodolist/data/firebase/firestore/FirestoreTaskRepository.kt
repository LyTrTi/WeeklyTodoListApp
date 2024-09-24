package com.example.weeklytodolist.data.firebase.firestore

import android.util.Log
import com.example.weeklytodolist.domain.TaskRepository
import com.example.weeklytodolist.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreTaskRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TaskRepository {
    private val _currentUser = auth.currentUser

    override fun getAllTaskFlow(): Flow<List<Task>> = callbackFlow {
        val tasksRef = firestore.collection("tasks")

        val subscription = tasksRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val tasks = snapshot.documents.mapNotNull {
                    it.toObject(Task::class.java)
                }
                trySend(tasks)
            }
        }

        awaitClose { subscription.remove() }
    }

//    {
//        val tasks = mutableListOf<Task>()
//        return lazy {
//            _currentUser?.let { user ->
//                firestore.collection("tasks")
//                    .whereEqualTo("userId", user.uid)
//                    .get()
//                    .addOnSuccessListener { documents ->
//                        for (document in documents) {
//                            val task = document.toObject(Task::class.java)
//                            tasks.add(task)
//                        }
//                    }
//                    .addOnFailureListener {
//                        // Handle error
//                    }
//            }
//            flow {
//                emit(tasks)
//            }
//        }.value
//
//    }

    override fun getTaskByIdFlow(taskId: Int): Flow<Task> = callbackFlow {
        val tasksRef = firestore.collection("tasks")

        val subscription = tasksRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val task = snapshot.documents.firstOrNull {
                    it.toObject(Task::class.java)?.id == taskId
                }?.toObject(Task::class.java)
                trySend(task ?: Task())
            }
        }

        awaitClose { subscription.remove() }
    }

    override suspend fun getAllTask(): List<Task> {
        val tasks = mutableListOf<Task>()
        _currentUser?.let { user ->
            firestore.collection("tasks")
                .whereEqualTo("userId", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val task = document.toObject(Task::class.java)
                        tasks.add(task)
                    }
                }
                .addOnFailureListener {
                    // Handle error
                }
        }
        return tasks
    }


    override suspend fun getTaskById(taskId: Int): Task {
        lateinit var task: Task
        _currentUser?.let { user ->
            firestore.collection("tasks")
                .whereEqualTo("userId", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    task = documents.firstOrNull {
                        it.toObject(Task::class.java).id == taskId
                    }?.toObject(Task::class.java) ?: Task()
                }
                .addOnFailureListener {
                    // Handle error
                }
        }
        return task
    }

    override suspend fun searchByName(taskName: String): List<Task> {
        lateinit var tasks: List<Task>
        _currentUser?.let { user ->
            firestore.collection("tasks")
                .whereEqualTo("userId", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    tasks = documents.map {
                        it.toObject(Task::class.java)
                    }.filter {
                        it.name.contains(taskName)
                    }
                }
                .addOnFailureListener {
                    // Handle error
                }
        }
        return tasks
    }

    override suspend fun deleteTask(taskId: Int) {
        val tasksRef = firestore.collection("tasks")

        tasksRef.document(taskId.toString())
            .delete()
            .addOnSuccessListener {
                // Handle success
                Log.d("FirestoreTaskRepository", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener {
                // Handle error
                Log.w("FirestoreTaskRepository", "Error deleting document", it)
            }
    }

    override suspend fun modifyTable(task: Task) {
        val tasksRef = firestore.collection("tasks")

        tasksRef.document(task.id.toString())
            .set(task)
            .addOnSuccessListener {
                // Handle success
                Log.d("FirestoreTaskRepository", "DocumentSnapshot successfully modified!")
            }
            .addOnFailureListener {
                // Handle error
                Log.w("FirestoreTaskRepository", "Error modifying document", it)
            }
    }

}