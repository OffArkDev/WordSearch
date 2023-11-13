package offarkdev.wordssearch.data

import kotlinx.serialization.Serializable


@Serializable
data class WordEntity(
    val pos: String,
    val word: String,
    val definitions: List<String>,
    val synonyms: String? = null,
)