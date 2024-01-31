package dev.syoritohatsuki.fstatsmobile.screens.profile

import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.data.dto.ApiMessage
import dev.syoritohatsuki.fstatsmobile.data.dto.User
import dev.syoritohatsuki.fstatsmobile.data.store.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.profile.components.ProfileHeader
import dev.syoritohatsuki.fstatsmobile.screens.profile.components.ProfileProjectItem
import dev.syoritohatsuki.fstatsmobile.screens.profile.dialog.CreateProjectDialog
import dev.syoritohatsuki.fstatsmobile.screens.profile.viewmodel.ProfileViewModel
import dev.syoritohatsuki.fstatsmobile.ui.theme.Blue
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    val json = Json { ignoreUnknownKeys = true }

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)
    val scope = rememberCoroutineScope()

    val token by dataStore.getToken.collectAsState("")

    val openAlertDialog = remember { mutableStateOf(false) }

    val profileViewModel: ProfileViewModel = viewModel()

    val projects by profileViewModel.projects.collectAsState()

    val loading = remember { mutableStateOf(false) }

    val user: User = json.decodeFromString(
        Base64.decode(
            if (token.contains(".")) token.split(".")[1] else return,
            Base64.URL_SAFE
        ).toString(Charsets.UTF_8)
    )

    profileViewModel.getUserProjects(user.id)

    if (openAlertDialog.value) CreateProjectDialog(
        onDismissRequest = { openAlertDialog.value = false },
        onConfirmation = {
            scope.launch {
                loading.value = true
                profileViewModel.createProject(it, token).catch {
                    Toast.makeText(
                        context,
                        (it as ResponseException).response.body<ApiMessage>().message,
                        Toast.LENGTH_SHORT
                    ).show()
                }.onCompletion {
                    openAlertDialog.value = false
                    loading.value = false
                    profileViewModel.getUserProjects(user.id)
                }.collect()
            }
        },
        loading = loading
    )

    Scaffold(
        modifier = Modifier.padding(16.dp),
        topBar = {
            ProfileHeader(user, dataStore)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(projects) {
                    ProfileProjectItem(navController = navController, project = it)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Blue,
                onClick = {
                    openAlertDialog.value = true
                },
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    )
}