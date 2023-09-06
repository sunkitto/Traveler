package com.sunkitto.traveler.feature.equipmentsDetailed

import android.content.res.Configuration
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
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sunkitto.traveler.R
import com.sunkitto.traveler.feature.equipmentsDetailed.model.EquipmentUi
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.LoadingContent
import com.sunkitto.traveler.ui.designSystem.TravelerButton
import com.sunkitto.traveler.ui.designSystem.TravelerIconButton
import com.sunkitto.traveler.ui.theme.TravelerTheme
import java.util.Currency

@Composable
fun EquipmentsDetailedScreen(
    state: EquipmentsDetailedState,
    onEquipmentFavorite: () -> Unit,
    onEquipmentDelete: () -> Unit,
    onBottomSheetEvent: (EquipmentsDetailedEvent) -> Unit,
    onBack: () -> Unit,
) {
    val scroll = rememberScrollState()
    val locale = LocalContext.current.resources.configuration.locales.get(0)
    val currency = Currency.getInstance(locale).currencyCode

    var showAddToCartBottomSheet by remember { mutableStateOf(false) }

    val orientation = LocalConfiguration.current.orientation

    if (showAddToCartBottomSheet) {
        AddToCartBottomSheet(
            state = state,
            onBottomSheetEvent = onBottomSheetEvent,
            onDismissRequest = {
                showAddToCartBottomSheet = false
            },
        )
    }

    when {
        state.isLoading -> {
            LoadingContent()
        }
        state.errorMessage != null -> {
            EmptyContent(state.errorMessage)
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f, false)
                        .verticalScroll(scroll, true),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                        600.dp
                                    } else {
                                        300.dp
                                    },
                                ),
                            model = state.equipment.image,
                            contentScale = ContentScale.Crop,
                            contentDescription = stringResource(id = R.string.equipment),
                        )
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 25.dp, vertical = 40.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
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
                                icon = if (state.equipment.isFavourite) {
                                    Icons.Rounded.Star
                                } else {
                                    Icons.Outlined.StarOutline
                                },
                                onClick = {
                                    onEquipmentFavorite()
                                },
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 25.dp,
                                        vertical = 40.dp,
                                    )
                                    .fillMaxSize(),
                            ) {
                                Text(
                                    modifier = Modifier.padding(bottom = 30.dp),
                                    text = state.equipment.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    modifier = Modifier.height(IntrinsicSize.Min),
                                    text = state.equipment.description,
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
                            .padding(20.dp),
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "${state.equipment.cost} $currency",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        TravelerButton(
                            modifier = Modifier,
                            onClick = {
                                if (state.equipment.isOrdered) {
                                    onEquipmentDelete()
                                } else {
                                    showAddToCartBottomSheet = true
                                }
                            },
                            text = if (state.equipment.isOrdered) {
                                stringResource(id = R.string.remove_from_cart)
                            } else {
                                stringResource(id = R.string.add_to_cart)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EquipmentsDetailedScreenPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    TravelerTheme {
        EquipmentsDetailedScreen(
            state = EquipmentsDetailedState(
                equipment = EquipmentUi(
                    id = "",
                    name = "Equipment 1",
                    image = "",
                    description = text,
                    cost = 20,
                    categoryId = "",
                    favouriteId = "",
                    isFavourite = true,
                    orderId = "",
                    isOrdered = false,
                ),
            ),
            onEquipmentFavorite = {},
            onEquipmentDelete = {},
            onBottomSheetEvent = {},
            onBack = {},
        )
    }
}