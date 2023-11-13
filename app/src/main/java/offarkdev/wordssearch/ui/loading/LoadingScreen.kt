package offarkdev.wordssearch.ui.loading

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import offarkdev.wordssearch.R
import offarkdev.wordssearch.ui.loading.DataSetState.Loading

@Composable
fun LoadingScreen(component: LoadDataScreenComponent) {

    val context = LocalContext.current
    LaunchedEffect(true) {
        component.load(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val state by component.model.subscribeAsState()

            val message = if (state is Error) (state as Error).message.orEmpty()
            else {
                val progress = (state as? Loading)?.progress
                val progressText = if (progress == null) "" else "$progress%"
                stringResource(R.string.converting_dictionary_from_json_to_database) + progressText
            }
            Text(modifier = Modifier.fillMaxWidth(), text = message, textAlign = TextAlign.Center)

            if (state is Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(48.dp),
                )
            }
        }
    }
}

@Composable
@Preview
fun LoadingScreenPreview() {
    LoadingScreen(component = object : LoadDataScreenComponent {
        override val model: Value<DataSetState> = MutableValue(Loading(1))
        override fun load(context: Context) {}
    })
}