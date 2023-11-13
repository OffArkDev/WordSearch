package offarkdev.wordssearch.ui.search

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import offarkdev.wordssearch.R
import offarkdev.wordssearch.ui.search.SearchState.Error
import offarkdev.wordssearch.ui.search.SearchState.Loading
import offarkdev.wordssearch.ui.search.SearchState.NoSuchWord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(component: SearchScreenComponent) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state by component.model.subscribeAsState()

            var searchText by remember { mutableStateOf("") }
            val context = LocalContext.current

            Text(modifier = Modifier.fillMaxWidth(), text = "Type text below. For example \"abject\"")

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {


                TextField(modifier = Modifier.fillMaxWidth(),
                    value = searchText,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "icon_search",
                            tint = Color.Black
                        )
                    },
                    supportingText = {
                        if (state is Error) {
                            Text(text = (state as Error).message, color = Color.Red)
                        } else if (state is NoSuchWord) Text(
                            text = stringResource(R.string.there_is_no_such_word_in_the_database),
                            color = Color.Red
                        )
                    },
                    onValueChange = {
                        if (it.length <= WORD_MAX_LENGTH) searchText = it
                    })
            }

            Button(modifier = Modifier.padding(top = 16.dp),
                enabled = searchText.isNotEmpty(),
                onClick = { component.searchInput(searchText, context) }) {
                Text(text = stringResource(R.string.search))
            }

            if (state is Loading) CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))


        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    SearchScreen(component = object : SearchScreenComponent {
        override val model: Value<SearchState> = MutableValue(NoSuchWord)
        override fun searchInput(text: String, context: Context) {}
    })
}

const val WORD_MAX_LENGTH = 45