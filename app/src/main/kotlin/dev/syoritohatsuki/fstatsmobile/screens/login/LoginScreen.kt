package dev.syoritohatsuki.fstatsmobile.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.syoritohatsuki.fstatsmobile.data.dto.ApiMessage
import dev.syoritohatsuki.fstatsmobile.data.store.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.login.viewmodel.LoginViewModel
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserToken(context)

    val loginViewModel: LoginViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var loading by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = username,
            placeholder = {
                Text(text = "Username", color = Color.LightGray)
            },
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            placeholder = {
                Text(text = "Password", color = Color.LightGray)
            },
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = username.length >= 3 && password.length >= 3 && !loading,
            onClick = {
                scope.launch {
                    loading = true
                    loginViewModel.login(username, password).catch {
                        Toast.makeText(
                            context,
                            (it as ClientRequestException).response.body<ApiMessage>().message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }.onCompletion {
                        loading = false
                    }.collect { token ->
                        dataStore.saveToken(token)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (loading) "Loading..." else "Login")
        }
    }
}