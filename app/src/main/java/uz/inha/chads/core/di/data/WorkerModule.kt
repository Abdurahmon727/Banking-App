package uz.inha.chads.core.di.data

import androidx.work.WorkManager
import uz.inha.chads.data.transactions.TransactionWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    single {
        WorkManager.getInstance(androidApplication().applicationContext)
    }

    worker {
        TransactionWorker(
            appContext = get(),
            workerParams = get(),
            transactionDao = get(),
            accountRepository = get(),
            transactionNotificationHelper = get()
        )
    }
}