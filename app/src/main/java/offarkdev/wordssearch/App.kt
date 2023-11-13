package offarkdev.wordssearch

import android.app.Application
import offarkdev.wordssearch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }

    }
}