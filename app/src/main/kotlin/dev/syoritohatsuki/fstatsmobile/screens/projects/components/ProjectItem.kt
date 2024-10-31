package dev.syoritohatsuki.fstatsmobile.screens.projects.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.data.dto.User

@Composable
fun ProjectItem(navController: NavController, project: Project) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Row(modifier = Modifier.clickable {
            navController.navigate("project/${project.id}")
        }, verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    text = project.name,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    text = project.owner.username
                )
            }
            if (!project.isVisible) Icon(
                Icons.Outlined.Info,
                "Project is hide",
                Modifier
                    .size(36.dp)
                    .padding(end = 8.dp),
                Color.Yellow
            )
        }
    }
}

@Preview
@Composable
fun ProjectItemPreview() {
    ProjectItem(
        rememberNavController(), Project(
            name = "Preview Project", owner = User(
                username = "Preview Name"
            )
        )
    )
}