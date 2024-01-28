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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.syoritohatsuki.fstatsmobile.screens.about.AboutScreen
import dev.syoritohatsuki.fstatsmobile.screens.favorite.FavoriteScreen
import dev.syoritohatsuki.fstatsmobile.screens.login.LoginScreen
import dev.syoritohatsuki.fstatsmobile.screens.profile.ProfileScreen
import dev.syoritohatsuki.fstatsmobile.screens.project.ProjectScreen
import dev.syoritohatsuki.fstatsmobile.screens.projects.ProjectsScreen
import dev.syoritohatsuki.fstatsmobile.ui.theme.Blue
import dev.syoritohatsuki.fstatsmobile.ui.theme.Dark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)
    val token = dataStore.getToken.collectAsState("")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("fStats Mobile")
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
                        Icon(Icons.Filled.Info, "About", tint = Color.White)
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
                        }, colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Blue,
                            selectedTextColor = Blue,
                            indicatorColor = Dark,
                            unselectedIconColor = Color.LightGray,
                            unselectedTextColor = Color.LightGray,
                        )
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
                AboutScreen()
            }
            composable(Screens.Favorite.route) {
                if (token.value.isBlank()) LoginScreen() else FavoriteScreen(navController)
            }
            composable(Screens.Projects.route) {
                ProjectsScreen(navController)
            }
            composable(Screens.Profile.route) {
                if (token.value.isBlank()) LoginScreen() else ProfileScreen(navController)
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