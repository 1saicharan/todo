package com.android.learning.todo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.learning.todo.data.Task
import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.data.room.TodoDatabase
import com.android.learning.todo.data.room.UserDao
import com.android.learning.todo.data.toTask
import com.android.learning.todo.ui.BottomNavigationBar
import com.android.learning.todo.ui.screens.CalenderScreen
import com.android.learning.todo.ui.screens.LoginScreen
import com.android.learning.todo.ui.screens.SignUpScreen
import com.android.learning.todo.ui.screens.TodoListScreen
import com.android.learning.todo.ui.theme.TodoTheme
import com.android.learning.todo.viewmodels.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    private val database by lazy { TodoDatabase.getDatabase(context = applicationContext) }
    private val userDao by lazy { database.userDao() }
    private val taskDao by lazy { database.taskDao() }
    private val taskViewModel by lazy { TaskViewModel(taskDao = taskDao) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isLoggedIn = remember { mutableStateOf(checkLoginState()) }

            TodoTheme {

                AppNavigation(userDao = userDao, taskViewModel = taskViewModel,isLoggedIn = isLoggedIn)




            }
        }
    }
    private fun checkLoginState(): Boolean {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLoggedIn", false)
    }
}

@Composable
fun AppNavigation(userDao: UserDao,taskViewModel: TaskViewModel,isLoggedIn:MutableState<Boolean>) {
    val navController = rememberNavController()
    Scaffold( bottomBar = {if(isLoggedIn.value)BottomNavigationBar(navController)},
        modifier = Modifier.navigationBarsPadding()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = if (!isLoggedIn.value) "login" else "todolistscreen/${LocalDate.now()}",
            ) {

                composable("login") {
                    LoginScreen(
                        userDao = userDao,
                        navController = navController,
                        isLoggedIn = isLoggedIn
                    )
                }
                composable(route = "signup") {
                    SignUpScreen(
                        userDao = userDao,
                        navController = navController
                    )
                }
                composable(route = "todolistscreen/{date}") { backStackEntry ->
                    val date =
                        LocalDate.parse(backStackEntry.arguments?.getString("date"))
                            ?: LocalDate.now()
                    TodoListScreen(
                        taskViewModel = taskViewModel,
                        navController = navController,
                        date = date
                    )
                }
                composable(route = "datepicker") { CalenderScreen(navHostController = navController) }
            }
        }
    }
}



