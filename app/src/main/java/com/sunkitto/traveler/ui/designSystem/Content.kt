package com.sunkitto.traveler.ui.designSystem

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptyContent(descriptionText: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = descriptionText,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )
    }
}

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("circularProgressBar"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark",
    showBackground = true,
)
@Composable
fun EmptyContentLightPreview() {
    EmptyContent("List is empty")
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark",
    showBackground = true,
)
@Composable
fun LoadingContentLightPreview() {
    LoadingContent()
}