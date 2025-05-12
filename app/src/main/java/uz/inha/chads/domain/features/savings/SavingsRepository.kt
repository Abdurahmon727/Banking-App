package uz.inha.chads.domain.features.savings

import uz.inha.chads.domain.features.savings.model.Saving

interface SavingsRepository {
    suspend fun getSavings(): List<Saving>
    suspend fun getSavingById(id: Long): Saving
}