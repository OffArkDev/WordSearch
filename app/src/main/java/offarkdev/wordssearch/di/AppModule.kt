package offarkdev.wordssearch.di

import app.cash.sqldelight.db.SqlDriver
import offarkdev.Database
import offarkdev.wordssearch.data.DataSource
import offarkdev.wordssearch.data.DataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory<DataSource> { DataSourceImpl(get()) }
    factory { Database(get()) }
    factory<SqlDriver> { createDriver(androidContext()) }
}
