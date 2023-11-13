package offarkdev.wordssearch.ui.loading

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import offarkdev.wordssearch.R
import offarkdev.wordssearch.coroutineScope
import offarkdev.wordssearch.data.DataSource
import offarkdev.wordssearch.inject
import offarkdev.wordssearch.ui.loading.DataSetState.Error
import offarkdev.wordssearch.ui.loading.DataSetState.Initial
import offarkdev.wordssearch.ui.loading.DataSetState.Loaded
import offarkdev.wordssearch.ui.loading.DataSetState.Loading
import timber.log.Timber
import java.util.Scanner


private const val ALL_WORDS_COUNT = 113376L

class DefaultLoadDataScreenComponent(
    private val componentContext: ComponentContext,
    private val onLoaded: () -> Unit,
) : LoadDataScreenComponent, ComponentContext by componentContext {

    private val scope = componentContext.coroutineScope()

    private val dataSource: DataSource by inject()

    private val state = MutableValue<DataSetState>(Initial)
    override val model: Value<DataSetState> = state

    private val handler = CoroutineExceptionHandler { _, throwable ->
        state.update { Error(throwable.toString()) }
        Timber.i("${throwable.message}")
    }


    override fun load(context: Context) {
        scope.launch(handler) {
            val countWords = dataSource.getSize()
            if (countWords != ALL_WORDS_COUNT) {
                subscribeProgress()
                jsonToDataBase(context)
                state.update { Loaded }
            }
            onLoaded()
        }
    }

    private suspend fun CoroutineScope.subscribeProgress() {
        launch {
            dataSource.progressState.collect { progress ->
                state.update {
                    Loading(progress)
                }
            }
        }

    }

    private suspend fun jsonToDataBase(context: Context) {
        val s = Scanner(context.resources.openRawResource(R.raw.dictionary)).useDelimiter("\\A")
        while (s.hasNext()) {
            dataSource.insertDataFromJson(s.next())
        }
    }

}

interface LoadDataScreenComponent {

    val model: Value<DataSetState>

    fun load(context: Context)
}

sealed class DataSetState {
    data object Initial : DataSetState()
    data class Loading(val progress: Int) : DataSetState()
    data object Loaded : DataSetState()
    data class Error(val message: String) : DataSetState()
}