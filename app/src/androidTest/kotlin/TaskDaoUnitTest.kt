import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.weeklytodolist.data.TaskDao
import com.example.weeklytodolist.data.TaskDatabase
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
@LargeTest
class TaskDaoUnitTest {
    private lateinit var taskDao: TaskDao
    private lateinit var taskDatabase: TaskDatabase

    private val task1 = Task(id = 1, name = "test task 1")
    private val task2 = Task(id = 2, name = "another task")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        taskDatabase =
            Room.inMemoryDatabaseBuilder(context = context, klass = TaskDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        taskDao = taskDatabase.getDao()
    }

    private fun taskDao_UpsertTask() = runBlocking {
        taskDao.modifyTable(task1)
        taskDao.modifyTable(task2)
    }

    @Test
    fun taskDao_getAllTask_returnTrue() {
        taskDao_UpsertTask()
        taskDao.getAllTaskFlow().map {
            assertEquals(task1, it[0])
            Log.d("Database after add:", "$it")
        }
    }

    @Test
    fun taskDao_searchTaskByName_returnExactNumberOfTask() = runBlocking {
        // Insert test data
        taskDao.modifyTable(task1)
        taskDao.modifyTable(task2)

        val taskName = "tes"
        val list = taskDao.searchByName(taskName)
        println("Tasks with name contain 'tes': $list")
        assertEquals(1, list.size)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        taskDatabase.close()
    }
}