package offarkdev.wordssearch.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import offarkdev.Database


fun createDriver(context: Context) = AndroidSqliteDriver(
    Database.Schema,
    context,
    "dictionary.db",
    callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
        override fun onConfigure(db: SupportSQLiteDatabase) {
            super.onConfigure(db)
            setPragma(db, "JOURNAL_MODE = WAL")
            setPragma(db, "SYNCHRONOUS = 2")
        }

        private fun setPragma(db: SupportSQLiteDatabase, pragma: String) {
            val cursor = db.query("PRAGMA $pragma")
            cursor.moveToFirst()
            cursor.close()
        }
    })
