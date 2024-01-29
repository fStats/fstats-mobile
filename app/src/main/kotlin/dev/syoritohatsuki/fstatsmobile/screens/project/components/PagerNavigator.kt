package dev.syoritohatsuki.fstatsmobile.screens.project.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerNavigator(type: MutableState<String>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            scope.launch {
                pagerState.scrollToPage(pagerState.currentPage - 1)
            }
        }, enabled = pagerState.currentPage > 0) {
            Icon(Icons.Filled.KeyboardArrowLeft, "")
        }
        Text(
            text = type.value.typeToName(),
            modifier = Modifier.padding(bottom = 2.dp),
            textAlign = TextAlign.Center
        )
        IconButton(onClick = {
            scope.launch {
                pagerState.scrollToPage(pagerState.currentPage + 1)
            }
        }, enabled = pagerState.currentPage < pagerState.pageCount - 1) {
            Icon(Icons.Filled.KeyboardArrowRight, "")
        }
    }
}

private fun String.typeToName(): String {
    return when (this) {
        "minecraft_version" -> "Minecraft Version"
        "online_mode" -> "Online Mode"
        "mod_version" -> "Mod Version"
        "os" -> "Operation System"
        "location" -> "Location"
        "fabric_api_version" -> "Fabric API"
        else -> this
    }
}