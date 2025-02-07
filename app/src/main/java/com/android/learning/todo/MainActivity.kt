package com.android.learning.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.learning.todo.data.room.TodoDatabase
import com.android.learning.todo.data.room.UserDao
import com.android.learning.todo.ui.screens.HomeScreen
import com.android.learning.todo.ui.screens.LoginScreen
import com.android.learning.todo.ui.screens.SignUpScreen
import com.android.learning.todo.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {
    private val database by lazy { TodoDatabase.getDatabase(context = applicationContext) }
    private val userDao by lazy { database.userDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(userDao = userDao)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(userDao: UserDao,){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {

        composable("login") { LoginScreen(userDao = userDao, navController = navController  ) }
        composable(route = "signup") { SignUpScreen(userDao = userDao, navController = navController) }
        composable(route = "home") { HomeScreen() }
    }
}
