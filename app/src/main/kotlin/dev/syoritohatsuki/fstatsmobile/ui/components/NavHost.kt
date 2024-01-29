package dev.syoritohatsuki.fstatsmobile.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.syoritohatsuki.fstatsmobile.Screens
import dev.syoritohatsuki.fstatsmobile.data.store.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.about.AboutScreen
import dev.syoritohatsuki.fstatsmobile.screens.favorite.FavoriteScreen
import dev.syoritohatsuki.fstatsmobile.screens.login.LoginScreen
import dev.syoritohatsuki.fstatsmobile.screens.profile.ProfileScreen
import dev.syoritohatsuki.fstatsmobile.screens.project.ProjectScreen
import dev.syoritohatsuki.fstatsmobile.screens.projects.ProjectsScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)
    val token = dataStore.getToken.collectAsState("")

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