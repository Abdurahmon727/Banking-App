package uz.inha.chads.data.signup

import uz.inha.chads.data.app.PrefKeys
import uz.inha.chads.domain.core.AppError
import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.otp.OtpRepository
import uz.inha.chads.domain.features.otp.model.OtpConfiguration
import uz.inha.chads.domain.features.otp.model.OtpVerificationResponse
import uz.inha.chads.domain.features.signup.SignUpPayload
import uz.inha.chads.domain.features.signup.SignUpRepository
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SignUpRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val otpRepository: OtpRepository,
    private val prefs: KsPrefs
) : SignUpRepository {
    override suspend fun signUpWithEmail(payload: SignUpPayload) =
        withContext(coroutineDispatcher) {
            delay(MOCK_DELAY)

            if (payload.email == MOCK_LOGIN_EMAIL && payload.password == MOCK_PASSWORD) {
                return@withContext
            } else {
                throw AppError(ErrorType.UNKNOWN_ERROR)
            }
        }

    override suspend fun confirmSignUpWithEmail(
        otpCode: String, otpConfiguration: OtpConfiguration
    ): OtpVerificationResponse = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        val signUpResult = OperationResult.runWrapped {
            otpRepository.verifyOtpCode(
                otpConfiguration = otpConfiguration, code = otpCode
            )
        }

        when (signUpResult) {
            is OperationResult.Success -> {
                // Successful signup
                if (signUpResult.data.isSuccess) {
                    prefs.push(PrefKeys.IS_LOGGED_IN.name, true)
                }

                return@withContext signUpResult.data
            }

            is OperationResult.Failure -> {
                throw signUpResult.error
            }
        }
    }

    companion object {
        private const val MOCK_LOGIN_EMAIL = "abdurahmon727.uz@gmail.com"
        private const val MOCK_PASSWORD = "ABC_12345678"
        private const val MOCK_DELAY = 1000L
    }
}