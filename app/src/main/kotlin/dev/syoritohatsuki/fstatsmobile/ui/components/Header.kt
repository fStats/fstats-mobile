package dev.syoritohatsuki.fstatsmobile.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.syoritohatsuki.fstatsmobile.BottomNavigationItem
import dev.syoritohatsuki.fstatsmobile.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isRootRoute = BottomNavigationItem().bottomNavigationItems().any {
        it.route == currentDestination?.route
    }

    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("fStats Mobile")
        },
        navigationIcon = {
            if (!isRootRoute) IconButton(navController::navigateUp) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        actions = {
            if (isRootRoute) IconButton({
                navController.navigate(Screens.About.route)
            }) {
                Icon(Icons.Filled.Info, "About", tint = Color.White)
            }
        }
    )
}