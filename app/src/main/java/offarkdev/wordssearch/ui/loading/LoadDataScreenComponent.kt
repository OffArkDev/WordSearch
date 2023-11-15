package offarkdev.wordssearch.ui.loading

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import offarkdev.wordssearch.coroutineScope
import offarkdev.wordssearch.data.DictionaryConverterRepo
import offarkdev.wordssearch.inject
import offarkdev.wordssearch.ui.loading.DataSetState.Error
import offarkdev.wordssearch.ui.loading.DataSetState.Initial
import offarkdev.wordssearch.ui.loading.DataSetState.Loaded
import offarkdev.wordssearch.ui.loading.DataSetState.Loading
import timber.log.Timber

class DefaultLoadDataScreenComponent(
    private val componentContext: ComponentContext,
    private val onLoaded: () -> Unit,
) : LoadDataScreenComponent, ComponentContext by componentContext {

    private val scope = componentContext.coroutineScope()

    private val converter: DictionaryConverterRepo by inject()

    private val state = MutableValue<DataSetState>(Initial)
    override val model: Value<DataSetState> = state

    private val handler = CoroutineExceptionHandler { _, throwable ->
        state.update { Error(throwable.toString()) }
        Timber.i("${throwable.message}")
    }


    override fun load() {
        scope.launch(handler) {
            subscribeProgress()
            converter()
            state.update { Loaded }
            onLoaded()
        }
    }

    private suspend fun CoroutineScope.subscribeProgress() {
        launch {
            converter.progressState.collect { progress ->
               if (progress > 0) state.update {
                    Loading(progress)
                }
            }
        }

    }

}

interface LoadDataScreenComponent {

    val model: Value<DataSetState>

    fun load()
}

sealed class DataSetState {
    data object Initial : DataSetState()
    data class Loading(val progress: Int) : DataSetState()
    data object Loaded : DataSetState()
    data class Error(val message: String) : DataSetState()
}