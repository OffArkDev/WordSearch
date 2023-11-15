package offarkdev.wordssearch.data.sql

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import offarkdev.Database
import offarkdev.WordEntityTable
import offarkdev.wordssearch.data.WordEntity

class SqlDataSourceImpl(private val database: Database) : SqlDataSource {

    override suspend fun getSize(): Long? {
        return database.Queries.getWordEntityTableSize().executeAsOneOrNull()
    }

    override suspend fun insertWordEntity(
        word: String,
        partOfSpeech: String,
        synonyms: String,
        definitions: String,
    ) {
        database.transaction {
            database.Queries.insertWordEntity(word, partOfSpeech, synonyms, definitions)
        }
    }

    override suspend fun getWord(text: String): List<WordEntityTable> {
        return withContext(Dispatchers.IO) {
            database.Queries.getWordByName(text.trim().uppercase()).executeAsList()
        }
    }

    override suspend fun deleteAllWords() {
        withContext(Dispatchers.IO) {
            database.Queries.deleteAllWords()
        }
    }

}