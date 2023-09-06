package com.sunkitto.traveler.feature.cart

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sunkitto.traveler.R
import com.sunkitto.traveler.feature.cart.model.OrderedEquipment
import com.sunkitto.traveler.ui.designSystem.EmptyContent
import com.sunkitto.traveler.ui.designSystem.LoadingContent
import com.sunkitto.traveler.ui.designSystem.OrderCard
import com.sunkitto.traveler.ui.designSystem.ScreenTitleText
import com.sunkitto.traveler.ui.designSystem.TravelerButton
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun CartScreen(
    uiState: CartState,
    onOrderCardEvent: (CartEvent) -> Unit,
    onOrderCardClick: (equipmentId: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp),
    ) {
        ScreenTitleText(
            modifier = Modifier.padding(vertical = 35.dp),
            text = stringResource(id = R.string.cart),
        )
        when {
            uiState.isLoading -> {
                LoadingContent()
            }
            uiState.orderedEquipments.isEmpty() && uiState.errorMessage == null -> {
                EmptyContent(
                    stringResource(id = R.string.cart_is_empty),
                )
            }
            uiState.errorMessage != null -> {
                EmptyContent(descriptionText = uiState.errorMessage)
            }
            else -> {
                OrdersList(
                    uiState = uiState,
                    orderedEquipments = uiState.orderedEquipments,
                    onOrderCardEvent = { cartEvent ->
                        onOrderCardEvent(cartEvent)
                    },
                    onOrderCardClick = onOrderCardClick,
                )
            }
        }
    }
}

@Composable
fun OrdersList(
    uiState: CartState,
    orderedEquipments: List<OrderedEquipment>,
    onOrderCardEvent: (CartEvent) -> Unit,
    onOrderCardClick: (equipmentId: String) -> Unit,
) {
    val locale = LocalContext.current.resources.configuration.locales.get(0)
    val currency = java.util.Currency.getInstance(locale).currencyCode

    var showSuccessOrderDialog by remember { mutableStateOf(false) }

    if (showSuccessOrderDialog) {
        SuccessOrderDialog(
            onDismissDialog = {
                showSuccessOrderDialog = false
            },
        )
    }

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
        ) {
            items(
                items = orderedEquipments,
                itemContent = { item ->
                    OrderCard(
                        modifier = Modifier.padding(bottom = 25.dp),
                        orderedEquipment = item,
                        onOrderCardEvent = { cartEvent ->
                            onOrderCardEvent(cartEvent)
                        },
                        onOrderCardClick = { equipmentId ->
                            onOrderCardClick(equipmentId)
                        },
                    )
                },
            )
        }
        Divider(color = Color.DarkGray, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.total) + " ${uiState.totalPrice} $currency",
                style = MaterialTheme.typography.headlineSmall,
            )
            Button(
                modifier = Modifier.height(50.dp),
                onClick = {
                    // Send to server
                    showSuccessOrderDialog = true
                },
                shape = MaterialTheme.shapes.medium,
                content = {
                    Text(
                        text = stringResource(id = R.string.rent),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )
        }
    }
}

@Composable
fun SuccessOrderDialog(
    onDismissDialog: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismissDialog()
        },
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        ) {
            Column {
                Column {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = stringResource(id = R.string.done),
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .height(70.dp)
                            .fillMaxWidth(),
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.success_rent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(
                                    top = 10.dp,
                                    start = 25.dp,
                                    end = 25.dp,
                                )
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TravelerButton(
                        modifier = Modifier
                            .padding(
                                top = 20.dp,
                                bottom = 40.dp,
                                start = 25.dp,
                                end = 25.dp,
                            )
                            .height(50.dp)
                            .fillMaxWidth(),
                        onClick = {
                            onDismissDialog()
                        },
                        text = stringResource(id = R.string.ok),
                    )
                }
            }
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
fun CartScreenPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    TravelerTheme {
        Surface {
            CartScreen(
                uiState = CartState(
                    orderedEquipments = List(10) {
                        OrderedEquipment(
                            id = "",
                            name = text,
                            image = "",
                            description = "",
                            cost = 0,
                            orderId = "",
                            count = 10,
                            days = 5,
                            price = 0,
                        )
                    },
                ),
                onOrderCardEvent = {},
                onOrderCardClick = {},
            )
        }
    }
}