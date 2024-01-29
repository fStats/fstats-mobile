package dev.syoritohatsuki.fstatsmobile.screens.profile

import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.data.dto.User
import dev.syoritohatsuki.fstatsmobile.data.store.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.profile.components.ProfileHeader
import dev.syoritohatsuki.fstatsmobile.screens.profile.viewmodel.ProfileViewModel
import dev.syoritohatsuki.fstatsmobile.screens.projects.components.ProjectItem
import kotlinx.serialization.json.Json

@Composable
fun ProfileScreen(navController: NavController) {

    val json = Json { ignoreUnknownKeys = true }

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)

    val token by dataStore.getToken.collectAsState("")

    val profileViewModel: ProfileViewModel = viewModel()

    val projects by profileViewModel.projects.collectAsState()

    val user: User = json.decodeFromString(
        Base64.decode(
            if (token.contains(".")) token.split(".")[1] else return,
            Base64.URL_SAFE
        ).toString(Charsets.UTF_8)
    )

    Column(Modifier.padding(16.dp)) {
        profileViewModel.getUserProjects(user.id)

        ProfileHeader(user, dataStore)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(projects) {
                ProjectItem(navController = navController, project = it)
            }
        }
    }
}