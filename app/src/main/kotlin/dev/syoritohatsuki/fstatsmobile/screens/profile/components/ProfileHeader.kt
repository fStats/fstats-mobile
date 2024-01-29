package dev.syoritohatsuki.fstatsmobile.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.syoritohatsuki.fstatsmobile.data.dto.User
import dev.syoritohatsuki.fstatsmobile.data.store.StoreUserToken
import kotlinx.coroutines.launch

@Composable
fun ProfileHeader(user: User, dataStore: StoreUserToken) {

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(text = "Username: ", fontWeight = FontWeight.Bold)
            Text(text = user.username)
        }
        Button(onClick = {
            scope.launch {
                dataStore.saveToken("")
            }
        }) {
            Text(text = "Logout")
        }
    }
}