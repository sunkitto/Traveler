package com.sunkitto.traveler.ui.designSystem

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sunkitto.traveler.R
import com.sunkitto.traveler.domain.model.Category
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.feature.cart.CartEvent
import com.sunkitto.traveler.feature.cart.model.OrderedEquipment
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: (
        categoryId: String,
        categoryName: String,
    ) -> Unit,
) {
    Card(
        modifier = modifier
            .height(130.dp)
            .clickable {
                onCategoryClick(category.id, category.name)
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(category.image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.category),
                contentScale = ContentScale.Crop,
            )
            Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = category.name,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
fun EquipmentCard(
    modifier: Modifier = Modifier,
    equipment: Equipment,
    onEquipmentClick: (equipmentId: String) -> Unit,
) {
    val locale = LocalContext.current.resources.configuration.locales.get(0)
    val currency = java.util.Currency.getInstance(locale).currencyCode

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                onEquipmentClick(equipment.id)
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Row {
            Surface(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp)
                    .fillMaxHeight(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(equipment.image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.equipment),
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = equipment.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(horizontalArrangement = Arrangement.End) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${equipment.cost} $currency",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    modifier: Modifier,
    orderedEquipment: OrderedEquipment,
    onOrderCardEvent: (CartEvent) -> Unit,
    onOrderCardClick: (equipmentId: String) -> Unit,
) {
    val locale = LocalContext.current.resources.configuration.locales.get(0)
    val currency = java.util.Currency.getInstance(locale).currencyCode

    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .height(120.dp)
                .clickable {
                    onOrderCardClick(orderedEquipment.id)
                },
            shape = MaterialTheme.shapes.medium,
        ) {
            Row {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(10.dp),
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(orderedEquipment.image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(id = R.string.equipment),
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = orderedEquipment.name,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = "${orderedEquipment.price} $currency",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(bottom = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.quantity),
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TravelerIconButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Remove,
                    contentDescription = stringResource(id = R.string.decrease_quantity),
                ) {
                    onOrderCardEvent(
                        CartEvent.OnDecreaseEquipmentsCount(id = orderedEquipment.id),
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = orderedEquipment.count.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
                TravelerIconButton(
                    modifier = Modifier.padding(start = 20.dp),
                    icon = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.increase_quantity),
                ) {
                    onOrderCardEvent(
                        CartEvent.OnIncreaseEquipmentsCount(id = orderedEquipment.id),
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(bottom = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.days),
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TravelerIconButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Remove,
                    contentDescription = stringResource(id = R.string.decrease_rent_days),
                ) {
                    onOrderCardEvent(
                        CartEvent.OnDecreaseRentDays(id = orderedEquipment.id),
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = orderedEquipment.days.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
                TravelerIconButton(
                    modifier = Modifier.padding(start = 20.dp),
                    icon = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.increase_rent_days),
                ) {
                    onOrderCardEvent(
                        CartEvent.OnIncreaseRentDays(id = orderedEquipment.id),
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "Light",
    showBackground = true,
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark",
    showBackground = true,
)
@Composable
fun CategoryCardPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    TravelerTheme {
        Surface {
            CategoryCard(
                modifier = Modifier
                    .padding(10.dp)
                    .width(170.dp),
                category = Category(
                    id = "",
                    name = text,
                    image = "",
                ),
                onCategoryClick = { _, _ -> },
            )
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "Light",
    showBackground = true,
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark",
    showBackground = true,
)
@Composable
fun EquipmentCardPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    TravelerTheme {
        Surface {
            EquipmentCard(
                modifier = Modifier.padding(10.dp),
                equipment = Equipment(
                    id = "",
                    name = text,
                    image = "",
                    description = "",
                    cost = 0,
                    categoryId = "",
                ),
                onEquipmentClick = { _ -> },
            )
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "Light",
    showBackground = true,
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark",
    showBackground = true,
)
@Composable
fun OrderCardPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    TravelerTheme {
        Surface {
            OrderCard(
                modifier = Modifier.padding(10.dp),
                orderedEquipment = OrderedEquipment(
                    id = "",
                    name = text,
                    image = "",
                    description = "",
                    cost = 0,
                    orderId = "",
                    count = 10,
                    days = 5,
                    price = 0,
                ),
                onOrderCardEvent = {},
                onOrderCardClick = {},
            )
        }
    }
}