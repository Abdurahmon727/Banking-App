package uz.inha.chads.data.login

import android.content.SharedPreferences
import uz.inha.chads.data.app.PrefKeys
import uz.inha.chads.domain.core.AppError
import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.features.login.LoginRepository
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val prefs: KsPrefs,
    private val securedPrefs: SharedPreferences
) : LoginRepository {

    override suspend fun loginWithEmail(email: String, password: String) =
        withContext(coroutineDispatcher) {
            delay(MOCK_DELAY)

            // TODO login attempts
            if (email == MOCK_LOGIN_EMAIL && password == MOCK_PASSWORD) {
                prefs.push(PrefKeys.IS_LOGGED_IN.name, true)
                return@withContext
            } else if (email != MOCK_LOGIN_EMAIL) {
                throw AppError(ErrorType.USER_NOT_FOUND)
            } else {
                throw AppError(ErrorType.WRONG_PASSWORD)
            }
        }

    override suspend fun checkIfLoggedIn(): Boolean {
        delay(MOCK_DELAY)
        return prefs.pull(PrefKeys.IS_LOGGED_IN.name, false)
    }

    override suspend fun logOut() = withContext(coroutineDispatcher) {
        // here may be some logic like cleanup and logout api call
        delay(MOCK_DELAY)

        // Clear app settings
        prefs.push(PrefKeys.IS_LOGGED_IN.name, false)

        // Clear encrypted storage
        securedPrefs.edit().clear().apply()
    }

    companion object {
        private const val MOCK_LOGIN_EMAIL = "abdurahmon727.uz@gmail.com"
        private const val MOCK_PASSWORD = "ABC_12345678"
        private const val MOCK_DELAY = 1000L
    }
}