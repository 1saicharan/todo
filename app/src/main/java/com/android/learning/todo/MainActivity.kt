package com.android.learning.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.learning.todo.data.Task
import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.data.room.TodoDatabase
import com.android.learning.todo.data.room.UserDao
import com.android.learning.todo.data.toTask
import com.android.learning.todo.ui.screens.HomeScreen
import com.android.learning.todo.ui.screens.LoginScreen
import com.android.learning.todo.ui.screens.SignUpScreen
import com.android.learning.todo.ui.theme.TodoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.tan

class MainActivity : ComponentActivity() {
    private val database by lazy { TodoDatabase.getDatabase(context = applicationContext) }
    private val userDao by lazy { database.userDao() }
    private val taskDao by lazy { database.taskDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                Scaffold() { innerPadding ->
                    Column ( modifier = Modifier.fillMaxSize()
                        .padding(paddingValues = innerPadding)
                    ){
                        AppNavigation(userDao = userDao,taskDao = taskDao)
                    }

                }

            }
        }
    }
}

@Composable
fun AppNavigation(userDao: UserDao,taskDao: TaskDao){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {

        composable("login") { LoginScreen(userDao = userDao, navController = navController  ) }
        composable(route = "signup") { SignUpScreen(userDao = userDao, navController = navController) }
        composable(route = "home") { HomeScreen(taskDao = taskDao, listOfItems = getListOfTasks(taskDao)) }
    }
}

private fun getListOfTasks(taskDao: TaskDao): List<Task> {
    return runBlocking(Dispatchers.IO) {
        taskDao.getTasks()?.map { it.toTask() } ?: emptyList()
    }
}
