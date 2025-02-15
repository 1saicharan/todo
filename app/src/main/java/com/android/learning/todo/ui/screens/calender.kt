package com.android.learning.todo.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderScreen(navHostController: NavHostController) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    LaunchedEffect(selectedDate) {
        if (selectedDate.isNotEmpty()) {
            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
            val formatedSelectedDate = LocalDate.parse(selectedDate, formatter).toString()
            val enCodedSelectedDate = Uri.encode(formatedSelectedDate)
            navHostController.navigate("todolistscreen/${enCodedSelectedDate}")
        }
    }
    Box(modifier = Modifier.fillMaxWidth().offset(y = 64.dp)
        .shadow(elevation = 4.dp)
        .background(MaterialTheme.colorScheme.surface)
        .padding(16.dp)) {
        DatePicker(

            state = datePickerState,
            showModeToggle = true
        )

    }

}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
