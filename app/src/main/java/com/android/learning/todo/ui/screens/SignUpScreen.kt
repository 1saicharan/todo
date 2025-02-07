package com.android.learning.todo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavHostController
import com.android.learning.todo.data.room.UserDao
import com.android.learning.todo.data.room.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//@Composable
//@Preview(showBackground = true)
//fun SignUpScreenPreview() {
//    SignUpScreen()
//}

@Composable
fun SignUpScreen(modifier: Modifier = Modifier,userDao: UserDao,navController:NavHostController) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxHeight()) {
        Spacer(modifier.weight(1f))
        Text("Sign Up",modifier = Modifier.fillMaxWidth().padding(3.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 3.dp),
            value = name,
            onValueChange = {name = it},
            label = { Text("Name") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 3.dp),
            value = username,
            onValueChange = {username = it},
            label = { Text("Username") },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 3.dp),
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
        )
        FilledTonalIconButton(onClick = {signup(userDao = userDao, userEntity = UserEntity(name = name,username = username, password =  password),navController = navController)},modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 1.dp), content = { Text("Signup") })
        Spacer(modifier.weight(6f))

    }
}

private fun signup(userDao: UserDao,userEntity: UserEntity,navController: NavHostController) {
    CoroutineScope(Dispatchers.Default).launch {
        userDao.insert(userEntity)
    }
    navController.navigate("login")
}