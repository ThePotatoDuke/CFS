package com.example.cfs.ui

import Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val items = listOf(
    BottomNavigationItem(
        title = "New Feedback",
        route = Routes.Request.name,
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.Add,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = "View Feedbacks",
        route = Routes.List.name,
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
        hasNews = false,
    )

)