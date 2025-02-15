package com.android.learning.todo.ui

import android.net.Uri
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.time.LocalDate

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screens.Tasks,
        Screens.Calendar,
        Screens.Profile
    )

    BottomNavigation(
        // Set background color
        backgroundColor = MaterialTheme.colors.surface,
        // Set elevation
        elevation = 8.dp
    ) {
        val currentRoute = currentRoute(navController)

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Helper to get current route
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

sealed class Screens(val route: String, val title: String, val icon: ImageVector) {
    object Tasks : Screens("todolistscreen/{date}", "Tasks", Icons.AutoMirrored.Filled.List)
    object Calendar : Screens("datepicker", "Calendar", Icons.Default.DateRange)
    object Profile : Screens("profile", "Profile", Icons.Default.Person)
}

