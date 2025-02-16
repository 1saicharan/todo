package com.android.learning.todo.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.learning.todo.data.User
import com.android.learning.todo.data.room.UserDao
import com.android.learning.todo.data.toUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileScreen(userId:Int?,navController: NavHostController,userDao: UserDao) {
    LaunchedEffect(userId) {
        Log.d("Profile_Debug", "User ID Changed: $userId")
    }
    var user:User
    if(userId!=null) {
        user = runBlocking(Dispatchers.IO) { userDao.getUserById(userId).toUser() }
    }
    else {
        user = User(0,"","","")
    }
    Scaffold() { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ProfileContent(user = user,navController = navController)
        }
    }

}

@Composable
fun ProfileContent(user:User,navController: NavHostController) {
    Column(Modifier.padding(5.dp)) {
        Text(text = "Profile Screen", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), fontSize = 25.sp)
        Text(text = "Name: ${user.name}",textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth(), fontSize = 20.sp)
        Text(text = "Username: ${user.username}",textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth(), fontSize = 20.sp)
        Button(onClick = { logout(navController = navController)}, modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp)) { Text("Logout") }
    }
}

private fun logout(navController: NavHostController) {
    val context = navController.context
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putBoolean("isLoggedIn", false)
        remove("userId")
        apply()
    }
    navController.navigate("login")
}
