package com.sunkitto.traveler.feature.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.sunkitto.traveler.R
import com.sunkitto.traveler.ui.designSystem.ScreenTitleText
import com.sunkitto.traveler.ui.designSystem.TravelerButton

@Composable
fun AccountScreen(
    state: AccountState,
    snackBarHostState: SnackbarHostState,
    onSignOutClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(12.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(data.visuals.message)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScreenTitleText(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 40.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.account)
            )
            if(state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    strokeCap = StrokeCap.Round
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(120.dp)
                        .fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.user?.profilePictureUrl)
                        .crossfade(true)
                        .transformations(
                            CircleCropTransformation()
                        )
                        .build(),
                    contentDescription = stringResource(id = R.string.category),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    text = state.user?.userName ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    text = state.user?.email ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                TravelerButton(
                    modifier = Modifier
                        .padding(vertical = 25.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.sign_out),
                    onClick = {
                        onSignOutClick()
                    }
                )
            }
        }
    }
}