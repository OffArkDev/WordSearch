package offarkdev.wordssearch.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import offarkdev.wordssearch.data.json.DictionaryDataSource
import offarkdev.wordssearch.data.sql.SqlDataSource

class DictionaryConverterRepo(
    private val jsonRepo: DictionaryDataSource,
    private val sqlDataSource: SqlDataSource,
) {

    val progressState: MutableStateFlow<Int> = MutableStateFlow(0)

    suspend operator fun invoke() {
        withContext(Dispatchers.Default) {
            val jsonData = jsonRepo.getDictionary()

            val loadedSize = sqlDataSource.getSize()?.toInt() ?: 0
            if (loadedSize >= jsonData.size) return@withContext

            jsonData.subList(loadedSize, jsonData.size).forEachIndexed { index, wordEntity ->
                progressState.value = calculateProgress(index, jsonData.size, loadedSize)

                sqlDataSource.insertWordEntity(
                    wordEntity.word,
                    wordEntity.pos,
                    wordEntity.synonyms.orEmpty(),
                    wordEntity.definitions.joinToString(", ")
                )
            }
        }
    }

    private fun calculateProgress(
        index: Int,
        dataSize: Int,
        loadedSize: Int,
    ) = ((index.toFloat() / (dataSize - loadedSize)) * 100).toInt()
}