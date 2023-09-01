package com.sunkitto.traveler.ui.designSystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sunkitto.traveler.R
import com.sunkitto.traveler.model.Category
import com.sunkitto.traveler.model.Equipment
import com.sunkitto.traveler.ui.theme.TravelerTheme

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: (
        categoryId: Int,
        categoryName: String,
    ) -> Unit,
) {
    Card(
        modifier = modifier
            .width(170.dp)
            .height(120.dp)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = category.name,
                        style = MaterialTheme.typography.bodyMedium,
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
    onEquipmentClick: (equipmentId: Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onEquipmentClick(equipment.id)
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Row {
            Surface(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(150.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(equipment.image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.equipment)
                )
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = equipment.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Row(horizontalArrangement = Arrangement.End) {
                    Text(
                        text = equipment.cost.toString() + "$",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    TravelerTheme {
        CategoryCard(
            modifier = Modifier.padding(10.dp),
            category = Category(name = "Category Card"),
            onCategoryClick = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EquipmentCardPreview() {
    TravelerTheme {
        EquipmentCard(
            modifier = Modifier.padding(10.dp),
            equipment = Equipment(
                name = "Equipment Card",
                cost = 20,
            ),
            onEquipmentClick = { _ -> }
        )
    }
}