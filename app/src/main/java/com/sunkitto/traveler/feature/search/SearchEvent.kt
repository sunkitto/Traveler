package com.sunkitto.traveler.feature.search

sealed interface SearchEvent {

    object SearchEquipments : SearchEvent
}