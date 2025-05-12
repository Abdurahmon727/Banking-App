package uz.inha.chads.domain.features.savings

import uz.inha.chads.domain.features.savings.model.Saving

class GetSavingByIdUseCase(
    private val savingsRepository: SavingsRepository
) {
    suspend fun execute(savingId: Long): Saving {
        return savingsRepository.getSavingById(savingId)
    }
}