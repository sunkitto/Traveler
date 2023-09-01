package com.sunkitto.traveler.domain.repository

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.model.Equipment
import kotlinx.coroutines.flow.Flow

interface EquipmentsRepository {

    fun getEquipments(): Flow<Result<List<Equipment>>>

    fun getEquipments(categoryId: Int): Flow<Result<List<Equipment>>>

    fun getEquipment(equipmentId: Int): Flow<Result<Equipment>>
}