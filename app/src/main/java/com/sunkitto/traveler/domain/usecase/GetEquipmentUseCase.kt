package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.repository.EquipmentsRepository
import com.sunkitto.traveler.model.Equipment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEquipmentUseCase @Inject constructor(
    private val equipmentsRepository: EquipmentsRepository
) {

    operator fun invoke(equipmentId: Int): Flow<Result<Equipment>> =
        equipmentsRepository.getEquipment(equipmentId = equipmentId)
}