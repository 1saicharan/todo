package com.android.learning.todo.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Composable
fun CalenderScreen() {

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DateButtonPreview(){
    DateButton(date = LocalDate.now())
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateButton(date:LocalDate = LocalDate.now()){
    OutlinedButton(onClick = {}) {
        Text(text = date.dayOfMonth.toString())
    }
}