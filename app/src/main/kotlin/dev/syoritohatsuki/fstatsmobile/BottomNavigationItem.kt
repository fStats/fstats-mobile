package dev.syoritohatsuki.fstatsmobile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> = listOf(
        BottomNavigationItem(
            label = "Favorite",
            icon = Icons.Filled.Favorite,
            route = Screens.Favorite.route
        ),
        BottomNavigationItem(
            label = "Projects",
            icon = Icons.Filled.List,
            route = Screens.Projects.route
        ),
        BottomNavigationItem(
            label = "Profile",
            icon = Icons.Filled.AccountCircle,
            route = Screens.Profile.route
        ),
    )
}