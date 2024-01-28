package dev.syoritohatsuki.fstatsmobile.screens.favorite

import android.util.Base64
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.favorite.viewmodel.FavoriteViewModel
import dev.syoritohatsuki.fstatsmobile.screens.projects.components.ProjectItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun FavoriteScreen(navController: NavController) {

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)

    val token = dataStore.getToken.collectAsState("")

    val favoriteViewModel: FavoriteViewModel = viewModel()

    val projects by favoriteViewModel.projects.collectAsState()

    val json = try {
        Json.parseToJsonElement(
            String(
                Base64.decode(
                    token.value.split(".")[1],
                    Base64.URL_SAFE
                ), charset("UTF-8")
            )
        )
    } catch (_: Exception) {
        null
    }

    Log.e("FAVORITE", "!")

    if (json != null && token.value.isNotBlank())
        favoriteViewModel.getUserFavorites(json.jsonObject["id"]!!.jsonPrimitive.int, token.value)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        projects.forEach {
            item {
                ProjectItem(navController, it)
            }
        }
    }
}