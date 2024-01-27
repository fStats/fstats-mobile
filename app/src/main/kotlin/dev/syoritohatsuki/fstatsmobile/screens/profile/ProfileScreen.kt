package dev.syoritohatsuki.fstatsmobile.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.StoreUserToken
import dev.syoritohatsuki.fstatsmobile.screens.login.LoginScreen

@Composable
fun ProfileScreen(navController: NavController) {

    val context = LocalContext.current
    val dataStore = StoreUserToken(context)

    if (dataStore.getToken.collectAsState("").value == "") {
        LoginScreen()
    } else {
        Text(text = "Profile Page")
    }

}
