package com.sunkitto.traveler.feature.equipmentsDetailed

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sunkitto.traveler.R
import com.sunkitto.traveler.model.Equipment
import com.sunkitto.traveler.ui.designSystem.TravelerButton
import com.sunkitto.traveler.ui.designSystem.TravelerIconButton
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun EquipmentsDetailedScreen(
    state: EquipmentsDetailedState,
    onEquipmentFavorite: () -> Unit,
    onEquipmentDelete: () -> Unit,
    onBack: () -> Unit,
) {

    val scroll = rememberScrollState()
    val orientation = LocalConfiguration.current.orientation

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f, false)
                .background(Color.White)
                .verticalScroll(scroll, true)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            if (orientation == ORIENTATION_LANDSCAPE) 600.dp
                            else 300.dp
                        ),
                    model = state.equipment?.image,
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(id = R.string.equipment)
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TravelerIconButton(
                        modifier = Modifier,
                        contentDescription = stringResource(id = R.string.back),
                        icon = Icons.Rounded.ArrowBack,
                        onClick = {
                            onBack()
                        },
                    )
                    TravelerIconButton(
                        modifier = Modifier,
                        contentDescription = stringResource(id = R.string.favourite),
                        icon = Icons.Rounded.Star ,
                        onClick = {
                            onEquipmentFavorite()
                        },
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 25.dp,
                                vertical = 40.dp
                            )
                            .fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 30.dp),
                            text = state.equipment?.name ?: "",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            modifier = Modifier.height(IntrinsicSize.Min),
                            text = state.equipment?.description ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
        Column {
            Divider(color = Color.DarkGray, thickness = 1.dp)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = state.equipment?.cost.toString() + "$",
                    style = MaterialTheme.typography.headlineSmall,
                )
                TravelerButton(
                    modifier = Modifier,
                    onClick = {
                        onEquipmentDelete()
                    },
                    text = stringResource(id = R.string.remove_from_cart),
                )
            }
        }
    }
}

@Preview
@Composable
fun EquipmentsDetailedScreenPreview() {
    TravelerTheme {
        EquipmentsDetailedScreen(
            state = EquipmentsDetailedState(
                equipment = Equipment()
            ),
            onEquipmentFavorite = {},
            onEquipmentDelete = {},
            onBack = {},
        )
    }
}