package com.sunkitto.traveler.feature.categories

sealed interface CategoriesEvent {

    object LoadCategories : CategoriesEvent

}