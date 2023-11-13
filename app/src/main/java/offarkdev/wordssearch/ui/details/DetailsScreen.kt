package offarkdev.wordssearch.ui.details

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.Parcelize

@Composable
fun DetailsScreen(data: List<WordData>, component: DetailsScreenComponent? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            items(data.size) {

                val item = data[it]

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.align(Center),
                        text = item.text,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "The word definition",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = item.definitions,
                    fontWeight = FontWeight.Normal
                )

                if (item.synonyms.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Synonyms for the word",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = item.synonyms,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }


        }

    }
}


@Composable
@Preview
fun DetailsScreenPreview() {
    DetailsScreen(
        data = listOf(
            WordData(
                "test",
                "this is a test definition",
                "test1, test2, test3, test4"
            )
        )
    )
}

@Parcelize
data class WordData(val text: String, val definitions: String, val synonyms: String) : Parcelable