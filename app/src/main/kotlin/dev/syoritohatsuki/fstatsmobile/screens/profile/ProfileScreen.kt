package dev.syoritohatsuki.fstatsmobile.screens.profile

import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.profile.viewmodel.ProfileViewModel
import dev.syoritohatsuki.fstatsmobile.screens.projects.components.ProjectItem
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun ProfileScreen(navController: NavController) {

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)
    val scope = rememberCoroutineScope()

    val token = dataStore.getToken.collectAsState("")

    val profileViewModel: ProfileViewModel = viewModel()

    val projects = profileViewModel.projects.collectAsState()

    Column(Modifier.padding(16.dp)) {

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(text = "Username: ", fontWeight = FontWeight.Bold)
                Text(text = json?.jsonObject?.get("username")?.jsonPrimitive?.content ?: "")
            }
            Button(onClick = {
                scope.launch {
                    dataStore.saveToken("")
                }
            }) {
                Text(text = "Logout")
            }
        }

        profileViewModel.getUserProjects(json?.jsonObject?.get("id")?.jsonPrimitive?.int ?: 1)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            projects.value.forEach {
                item {
                    ProjectItem(navController = navController, project = it)
                }
            }
        }
    }
}