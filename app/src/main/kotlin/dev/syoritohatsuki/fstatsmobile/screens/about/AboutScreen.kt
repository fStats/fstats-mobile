package dev.syoritohatsuki.fstatsmobile.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AboutScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "fStats Mobile", fontSize = 24.sp)
        Row {
            Text(text = "Developed by", fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(text = "Syorito Hatsuki", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = "Version: 2024.1.1", fontSize = 18.sp)
    }
}