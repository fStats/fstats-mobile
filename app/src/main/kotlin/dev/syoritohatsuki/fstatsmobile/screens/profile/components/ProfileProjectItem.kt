package dev.syoritohatsuki.fstatsmobile.screens.profile.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.syoritohatsuki.fstatsmobile.data.dto.ApiMessage
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.data.dto.User
import dev.syoritohatsuki.fstatsmobile.data.store.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.profile.dialog.DeleteProjectDialog
import dev.syoritohatsuki.fstatsmobile.screens.profile.viewmodel.ProfileViewModel
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@Composable
fun ProfileProjectItem(navController: NavController, project: Project) {

    val openAlertDialog = remember { mutableStateOf(false) }
    val profileViewModel: ProfileViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStore = StoreUserToken(context)
    val token by dataStore.getToken.collectAsState("")
    val loading = remember { mutableStateOf(false) }

    if (openAlertDialog.value) DeleteProjectDialog(
        onDismissRequest = { openAlertDialog.value = false },
        onConfirmation = {
            scope.launch {
                loading.value = true
                profileViewModel.deleteProject(project.id, token).catch {
                    Toast.makeText(
                        context,
                        (it as ResponseException).response.body<ApiMessage>().message,
                        Toast.LENGTH_SHORT
                    ).show()
                }.onCompletion {
                    openAlertDialog.value = false
                    loading.value = false
                    profileViewModel.getUserProjects(project.owner.id)
                }.collect()
            }
        },
        loading = loading
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.clickable {
                navController.navigate("project/${project.id}")
            }) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    text = project.name
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    text = project.owner.username
                )
            }
            IconButton(onClick = {
                openAlertDialog.value = true
            }) {
                Icon(Icons.Filled.Delete, "", tint = Color.Red)
            }
        }
    }
}

@Preview
@Composable
fun ProjectItemPreview() {
    ProfileProjectItem(
        rememberNavController(), Project(
            name = "Preview Project",
            owner = User(
                username = "Preview Name"
            )
        )
    )
}