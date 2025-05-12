package uz.inha.chads.domain.features.savings

import uz.inha.chads.domain.features.savings.model.Saving

class GetAllSavingsUseCase(
    private val savingsRepository: SavingsRepository
) {
    suspend fun execute(): List<Saving> {
        return savingsRepository.getSavings().sortedByDescending {
            it.completedPercentage
        }
    }
}