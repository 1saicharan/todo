package com.android.learning.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.data.room.TodoDatabase
import com.android.learning.todo.data.room.UserDao
import com.android.learning.todo.ui.BottomNavigationBar
import com.android.learning.todo.ui.screens.LoginScreen
import com.android.learning.todo.ui.screens.ProfileScreen
import com.android.learning.todo.ui.screens.SignUpScreen
import com.android.learning.todo.ui.screens.TodoListScreen
import com.android.learning.todo.ui.theme.TodoTheme
import com.android.learning.todo.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var userDao: UserDao

    private val taskViewModel:TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userId: Int = updateUserId(taskViewModel)
        setContent {
            val isLoggedIn = remember { mutableStateOf(checkLoginState()) }

            TodoTheme {

                AppNavigation(
                    userDao = userDao,
                    taskViewModel = taskViewModel,
                    isLoggedIn = isLoggedIn
                )


            }
        }
    }

    private fun checkLoginState(): Boolean {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        Log.d(
            "LoginState_Debug",
            "Retrieved Login State from SharedPreferences: ${
                sharedPref.getBoolean(
                    "isLoggedIn",
                    false
                )
            }"
        ) // Debug log
        return sharedPref.getBoolean("isLoggedIn", false)
    }

    private fun updateUserId(taskViewModel: TaskViewModel): Int {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)

        Log.d("UserID_Debug", "Retrieved User ID from SharedPreferences: $userId") // Debug log

        if (userId != -1) {
            taskViewModel.setUser(userId)
            Log.d("UserID_Debug", "User ID set in ViewModel: $userId") // Debug log
        } else {
            Log.e("UserID_Debug", "User ID not found in SharedPreferences")
        }
        return userId
    }

}

@Composable
fun AppNavigation(
    userDao: UserDao,
    taskViewModel: TaskViewModel,
    isLoggedIn: MutableState<Boolean>
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { if (isLoggedIn.value) BottomNavigationBar(navController) },
        modifier = Modifier.navigationBarsPadding()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = if (!isLoggedIn.value) "login" else "todolistscreen/${LocalDate.now()}",
            ) {

                composable("login") {
                    LoginScreen(
                        userDao = userDao,
                        navController = navController,
                        isLoggedIn = isLoggedIn,
                        taskViewModel = taskViewModel
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
                composable(route = "profile") {
                    val userId by taskViewModel.userId.collectAsState()
                    ProfileScreen(userId = userId, navController = navController, userDao = userDao)
                }
            }
        }
    }
}



