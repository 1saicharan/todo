package com.android.learning.todo.ui.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.learning.todo.data.room.UserDao
import com.android.learning.todo.viewmodels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

//@Preview(showBackground = true, widthDp = 360, heightDp = 640)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}

@Composable
fun LoginScreen(modifier: Modifier = Modifier,userDao: UserDao,navController:NavHostController,isLoggedIn:MutableState<Boolean>,taskViewModel: TaskViewModel) {
    val context = navController.context
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxHeight()) {
        Spacer(modifier.weight(2f))
        Text("Login Screen",modifier = Modifier.fillMaxWidth().padding(3.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 3.dp),
            value = username,
            onValueChange = {username = it},
            label = { Text("Username") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 3.dp),
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
        )
        FilledIconButton(onClick = { login(userDao = userDao,username = username,password = password,navController = navController,isLoggedIn  =  isLoggedIn,context,taskViewModel)},modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 1.dp), content = { Text("Login") })
        FilledTonalIconButton(onClick = { navController.navigate("signup") },modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 1.dp), content = { Text("Signup") })
        Spacer(modifier.weight(2f))

    }
}

private fun login(userDao: UserDao, username: String, password: String, navController: NavHostController,isLoggedIn: MutableState<Boolean>,context: Context,taskViewModel: TaskViewModel) {
    val enCodedtodayDate = Uri.encode(LocalDate.now().toString())
    CoroutineScope(Dispatchers.Default).launch {
        if(userDao.isUserExists(username) && userDao.login(username,password)){
            isLoggedIn.value = true
            val userId = userDao.getUserId(username,password)
            taskViewModel.setUser(userId)
            saveLoginState(isLoggedIn = isLoggedIn.value,context = context,userId)
            withContext(Dispatchers.Main){
                navController.navigate("todolistscreen/${enCodedtodayDate}"){
                    popUpTo("login"){
                        inclusive = true
                    }
                }
            }
        }
    }
}

fun saveLoginState(isLoggedIn: Boolean,context: Context,userId: Int) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putBoolean("isLoggedIn", isLoggedIn)
        putInt("userId",userId)
        apply()
    }

}
