package uz.inha.chads.core.di.data

import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import uz.inha.chads.data.account.AccountRepositoryMock
import uz.inha.chads.data.app.AppRepositoryImpl
import uz.inha.chads.data.app.AppSettignsRepository
import uz.inha.chads.data.app_lock.AppLockRepositoryImpl
import uz.inha.chads.data.cards.CardsRepositoryMock
import uz.inha.chads.data.contacts.ContactsRepositoryMock
import uz.inha.chads.data.db.CacheDatabase
import uz.inha.chads.data.db.convertors.MoneyAmountConvertor
import uz.inha.chads.data.login.LoginRepositoryMock
import uz.inha.chads.data.otp.OtpRepositoryMock
import uz.inha.chads.data.profile.ProfileRepositoryMock
import uz.inha.chads.data.savings.SavingsRepositoryMock
import uz.inha.chads.data.signup.SignUpRepositoryMock
import uz.inha.chads.data.transactions.TransactionRepositoryMock
import uz.inha.chads.domain.features.account.AccountRepository
import uz.inha.chads.domain.features.app_lock.AppLockRepository
import uz.inha.chads.domain.features.cards.CardsRepository
import uz.inha.chads.domain.features.contacts.ContactsRepository
import uz.inha.chads.domain.features.login.LoginRepository
import uz.inha.chads.domain.features.otp.OtpRepository
import uz.inha.chads.domain.features.profile.ProfileRepository
import uz.inha.chads.domain.features.savings.SavingsRepository
import uz.inha.chads.domain.features.signup.SignUpRepository
import uz.inha.chads.domain.features.transactions.TransactionRepository
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module


val dataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            CacheDatabase::class.java,
            "cache.db"
        )
            .fallbackToDestructiveMigration()
            .addTypeConverter(MoneyAmountConvertor())
            .build()
    }

    single {
        val db: CacheDatabase = get()
        db.getCardsDao()
    }

    single {
        val db: CacheDatabase = get()
        db.getTransactionsDao()
    }

    single<AppSettignsRepository> {
        AppRepositoryImpl(get())
    }

    single<SignUpRepository> {
        SignUpRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            otpRepository = get(),
            prefs = get()
        )
    }

    single<LoginRepository> {
        LoginRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            prefs = get(),
            securedPrefs = get(named("encryptedPrefs"))
        )
    }

    single<ProfileRepository> {
        ProfileRepositoryMock(dispatcher = Dispatchers.IO)
    }

    single<CardsRepository> {
        CardsRepositoryMock(
            cardsDao = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    single<SavingsRepository> {
        SavingsRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            context = androidApplication().applicationContext,
            cardsRepository = get()
        )
    }

    single<OtpRepository> {
        OtpRepositoryMock(
            coroutineDispatcher = Dispatchers.IO
        )
    }

    single {
        KsPrefs(androidApplication().applicationContext)
    }

    single(named("encryptedPrefs")) {
        val context = androidApplication().applicationContext

        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secured_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<AppLockRepository> {
        AppLockRepositoryImpl(
            securedPreferences = get(named("encryptedPrefs")),
            context = androidApplication().applicationContext
        )
    }

    single<AccountRepository> {
        AccountRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            cardsDao = get(),
        )
    }

    single<TransactionRepository> {
        TransactionRepositoryMock(
            workManager = get(),
            contactsRepository = get(),
            transactionDao = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    single<ContactsRepository> {
        ContactsRepositoryMock()
    }
}