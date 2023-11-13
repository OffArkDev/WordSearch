package offarkdev.wordssearch.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import offarkdev.Database
import offarkdev.WordEntityTable

class DataSourceImpl(private val database: Database) : DataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override val progressState: MutableStateFlow<Int> = MutableStateFlow(0)

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


    override suspend fun insertDataFromJson(jsonData: String) {
        withContext(Dispatchers.IO) {
            deleteAllWords()

            val jsonArray = json.decodeFromString<List<WordEntity>>(jsonData)

            jsonArray.forEachIndexed { index, wordEntity ->

                progressState.value = ((index.toFloat() / jsonArray.size) * 100).toInt()

                insertWordEntity(
                    wordEntity.word,
                    wordEntity.pos,
                    wordEntity.synonyms.orEmpty(),
                    wordEntity.definitions.joinToString(", ")
                )
            }
        }
    }

    override suspend fun getWord(text: String): List<WordEntityTable> {
        return withContext(Dispatchers.IO) {
            database.Queries.getWordByName(text.trim().uppercase()).executeAsList()
        }
    }

    private fun deleteAllWords() {
        database.Queries.deleteAllWords()
    }

}