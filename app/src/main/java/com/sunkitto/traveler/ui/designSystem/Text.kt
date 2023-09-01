package com.sunkitto.traveler.ui.designSystem

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun ScreenTitleText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Preview(showBackground = true)
@Composable
fun ScreenTitleTextPreview() {
    TravelerTheme {
        ScreenTitleText(
            modifier = Modifier.padding(10.dp),
            text = "Title Text",
        )
    }
}