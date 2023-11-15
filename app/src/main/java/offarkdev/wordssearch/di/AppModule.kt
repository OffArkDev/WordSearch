package offarkdev.wordssearch.di

import app.cash.sqldelight.db.SqlDriver
import offarkdev.Database
import offarkdev.wordssearch.data.DictionaryConverterRepo
import offarkdev.wordssearch.data.json.DictionaryDataSource
import offarkdev.wordssearch.data.json.DictionaryDataSourceImpl
import offarkdev.wordssearch.data.sql.SqlDataSource
import offarkdev.wordssearch.data.sql.SqlDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory<SqlDataSource> { SqlDataSourceImpl(get()) }
    factory { Database(get()) }
    factory<SqlDriver> { createDriver(androidContext()) }
    factory { DictionaryConverterRepo(get(), get()) }
    factory<DictionaryDataSource> { DictionaryDataSourceImpl(androidContext().resources) }
}
