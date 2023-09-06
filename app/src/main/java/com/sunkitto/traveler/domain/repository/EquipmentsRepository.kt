package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.Equipment
import kotlinx.coroutines.flow.Flow

interface EquipmentsRepository {

    fun getEquipments(): Flow<TravelerResult<List<Equipment>>>

    fun getEquipments(categoryId: String): Flow<TravelerResult<List<Equipment>>>

    fun getEquipment(equipmentId: String): Flow<TravelerResult<Equipment?>>
}