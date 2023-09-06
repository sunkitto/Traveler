package com.sunkitto.traveler.ui.designSystem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.feature.search.SearchState
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    uiState: SearchState,
    onTextChange: (query: String) -> Unit,
    onClear: () -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = uiState.searchQuery ?: "",
        onValueChange = { value ->
            onTextChange(value)
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(id = R.string.search),
            )
        },
        trailingIcon = {
            if (uiState.searchQuery != "") {
                IconButton(
                    onClick = {
                        onClear()
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.clear),
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium,
    )
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    TravelerTheme {
        SearchTextField(
            uiState = SearchState(),
            onTextChange = {},
            onClear = {},
        )
    }
}