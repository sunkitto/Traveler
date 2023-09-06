package com.sunkitto.traveler.ui.designSystem

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun TravelerButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(50.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        content = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
    )
}

@Composable
fun TravelerOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    leadingIcon: @Composable (() -> Unit)?,
    text: String,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun TravelerIconButton(
    modifier: Modifier = Modifier,
    contentDescription: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary,
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
            )
        }
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
fun TravelerButtonPreview() {
    TravelerTheme {
        TravelerButton(
            modifier = Modifier.padding(10.dp),
            onClick = {},
            text = stringResource(id = R.string.sign_in),
        )
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
fun TravelerOutlinedButtonPreview() {
    TravelerTheme {
        TravelerOutlinedButton(
            modifier = Modifier.padding(10.dp),
            onClick = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = null,
                )
            },
            text = stringResource(id = R.string.sign_in_with_google),
        )
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
fun TravelerIconButtonPreview() {
    TravelerTheme {
        TravelerIconButton(
            modifier = Modifier.padding(10.dp),
            contentDescription = "",
            icon = Icons.Rounded.Star,
            onClick = {},
        )
    }
}