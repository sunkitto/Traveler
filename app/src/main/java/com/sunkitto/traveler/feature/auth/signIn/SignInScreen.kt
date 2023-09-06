package com.sunkitto.traveler.feature.auth.signIn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunkitto.traveler.R
import com.sunkitto.traveler.ui.designSystem.ScreenTitleText
import com.sunkitto.traveler.ui.designSystem.TravelerOutlinedButton
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun SignInScreen(
    state: SignInState,
    snackBarHostState: SnackbarHostState,
    onGoogleSignInClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                Snackbar(modifier = Modifier.padding(12.dp)) {
                    Text(data.visuals.message)
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            ScreenTitleText(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 40.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.sign_in),
            )
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .testTag("signInProgressBar"),
                    strokeCap = StrokeCap.Round,
                )
            }
            TravelerOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp),
                onClick = {
                    onGoogleSignInClick()
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = stringResource(id = R.string.sign_in_with_google),
                    )
                },
                text = stringResource(id = R.string.sign_in_with_google),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    TravelerTheme {
        SignInScreen(
            SignInState(),
            SnackbarHostState(),
        ) { }
    }
}