package dev.syoritohatsuki.fstatsmobile.screens.favorite

import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.data.dto.User
import dev.syoritohatsuki.fstatsmobile.data.store.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.favorite.viewmodel.FavoriteViewModel
import dev.syoritohatsuki.fstatsmobile.screens.projects.components.ProjectItem
import kotlinx.serialization.json.Json

@Composable
fun FavoriteScreen(navController: NavController) {

    val json = Json { ignoreUnknownKeys = true }

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)

    val token by dataStore.getToken.collectAsState("")

    val favoriteViewModel: FavoriteViewModel = viewModel()

    val projects by favoriteViewModel.projects.collectAsState()

    val user: User = json.decodeFromString(
        Base64.decode(
            if (token.contains(".")) token.split(".")[1] else return,
            Base64.URL_SAFE
        ).toString(Charsets.UTF_8)
    )

    favoriteViewModel.getUserFavorites(user.id, token)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(projects) {
            ProjectItem(navController, it)
        }
    }
}