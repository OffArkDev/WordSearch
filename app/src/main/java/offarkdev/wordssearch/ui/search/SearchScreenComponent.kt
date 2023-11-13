package offarkdev.wordssearch.ui.search

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import offarkdev.WordEntityTable
import offarkdev.wordssearch.coroutineScope
import offarkdev.wordssearch.data.DataSource
import offarkdev.wordssearch.inject
import offarkdev.wordssearch.ui.search.SearchState.Error
import offarkdev.wordssearch.ui.search.SearchState.Idle
import offarkdev.wordssearch.ui.search.SearchState.Loading
import offarkdev.wordssearch.ui.search.SearchState.NoSuchWord
import offarkdev.wordssearch.ui.details.WordData
import timber.log.Timber

class DefaultSearchScreenComponent(
    private val componentContext: ComponentContext,
    private val onInput: (List<WordData>) -> Unit,
) : SearchScreenComponent, ComponentContext by componentContext {

    private val dataSource: DataSource by inject()

    private val scope = componentContext.coroutineScope()

    private val state = MutableValue<SearchState>(Idle)
    override val model = state

    private val handler = CoroutineExceptionHandler { _, throwable ->
        state.update { Error(throwable.message.toString()) }
        Timber.i("$throwable")
    }

    override fun searchInput(text: String, context: Context) {
        scope.launch(handler) {
            state.update { Loading }
            val data = dataSource.getWord(text)
            if (data.isNotEmpty()) {
                onInput(data.toViewData())
                state.update { Idle }
            }
            else state.update { NoSuchWord }

        }

    }

    private fun List<WordEntityTable>.toViewData() =
        map { WordData(it.word, it.definitions, it.synonyms) }
}


interface SearchScreenComponent {

    val model: Value<SearchState>

    fun searchInput(text: String, context: Context)
}

sealed class SearchState {
    data object NoSuchWord : SearchState()
    data class Error(val message: String) : SearchState()
    data object Loading : SearchState()
    data object Idle : SearchState()
}