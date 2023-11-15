package offarkdev.wordssearch.data.json

import android.content.res.Resources
import kotlinx.serialization.json.Json
import offarkdev.wordssearch.R
import offarkdev.wordssearch.data.WordEntity
import java.util.Scanner

class DictionaryDataSourceImpl(private val resources: Resources): DictionaryDataSource {

    private val json = Json { ignoreUnknownKeys = true }

   override fun getDictionary(): List<WordEntity> {
        val s = Scanner(resources.openRawResource(R.raw.dictionary)).useDelimiter("\\A")
        return if (s.hasNext()) json.decodeFromString(s.next()) else emptyList()
    }
}

interface DictionaryDataSource {
    fun getDictionary(): List<WordEntity>
}