package dev.syoritohatsuki.fstatsmobile.ui.projects.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProjectItem(projectName: String, ownerName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Column(modifier = Modifier
            .clickable {}
            .padding(8.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                text = projectName,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                text = ownerName
            )
        }
    }
}

@Preview
@Composable
fun ProjectItemPreview() {
    ProjectItem(projectName = "Project Name", ownerName = "Owner Name")
}