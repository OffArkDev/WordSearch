package offarkdev.wordssearch.data

import kotlinx.coroutines.flow.MutableStateFlow
import offarkdev.WordEntityTable

interface DataSource {

    val progressState: MutableStateFlow<Int>

    suspend fun insertWordEntity(
        word: String,
        partOfSpeech: String,
        synonyms: String,
        definitions: String,
    )

    suspend fun insertDataFromJson(jsonData: String)

    suspend fun getSize() : Long?

    suspend fun getWord(text: String): List<WordEntityTable>

}