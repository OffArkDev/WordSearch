package offarkdev.wordssearch.data.sql

import offarkdev.WordEntityTable

interface SqlDataSource {

    suspend fun insertWordEntity(
        word: String,
        partOfSpeech: String,
        synonyms: String,
        definitions: String,
    )

    suspend fun getSize() : Long?

    suspend fun getWord(text: String): List<WordEntityTable>

    suspend fun deleteAllWords()

}