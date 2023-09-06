package com.sunkitto.traveler.feature.search

sealed interface SearchEvent {

    data class SearchEquipments(val query: String) : SearchEvent

    object ClearSearch : SearchEvent
}