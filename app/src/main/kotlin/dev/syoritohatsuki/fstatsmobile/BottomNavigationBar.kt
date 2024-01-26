package dev.syoritohatsuki.fstatsmobile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.syoritohatsuki.fstatsmobile.screens.about.AboutScreen
import dev.syoritohatsuki.fstatsmobile.screens.project.ProjectScreen
import dev.syoritohatsuki.fstatsmobile.screens.projects.ProjectsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("fStats")
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) IconButton(navController::navigateUp) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                actions = {
                    if (navController.previousBackStackEntry == null) IconButton({
                        navController.navigate(Screens.About.route)
                    }) {
                        Icon(Icons.Filled.Info, "About")
                    }
                }
            )
        },
        bottomBar = {
            if (!BottomNavigationItem().bottomNavigationItems()
                    .any { it.route == currentDestination?.route }
            ) return@Scaffold

            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEach { navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(navigationItem.icon, navigationItem.label)
                        },
                        onClick = {
                            navController.popBackStack()
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Favorite.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screens.About.route) {
                AboutScreen(navController)
            }
            composable(Screens.Favorite.route) {
                Text(text = "Favorite")
            }
            composable(Screens.Projects.route) {
                ProjectsScreen(navController)
            }
            composable(Screens.Profile.route) {
                Text(text = "Profile")
            }
            composable(
                Screens.Project.route,
                arguments = listOf(navArgument(Screens.Project.argument) { type = NavType.IntType })
            ) {
                ProjectScreen(navController, it)
            }
        }
    }
}