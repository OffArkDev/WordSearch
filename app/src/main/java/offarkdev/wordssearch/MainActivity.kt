package offarkdev.wordssearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import offarkdev.wordssearch.ui.root.DefaultRootComponent
import offarkdev.wordssearch.ui.root.RootComponent
import offarkdev.wordssearch.ui.root.RootComponent.Child.Details
import offarkdev.wordssearch.ui.root.RootComponent.Child.Loading
import offarkdev.wordssearch.ui.root.RootComponent.Child.Search
import offarkdev.wordssearch.ui.details.DetailsScreen
import offarkdev.wordssearch.ui.loading.LoadingScreen
import offarkdev.wordssearch.ui.search.SearchScreen

class MainActivity : ComponentActivity() {

    private val root by lazy { DefaultRootComponent(defaultComponentContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { RootContent(root) }
    }

    @Composable
    fun RootContent(
        component: RootComponent,
        modifier: Modifier = Modifier,
    ) {
        Surface(modifier = modifier.fillMaxSize()) {
            Children(
                stack = component.stack,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val instance = it.instance) {
                    is Search -> SearchScreen(instance.component)
                    is Details -> DetailsScreen(instance.component)
                    is Loading -> LoadingScreen(instance.component)
                }
            }
        }
    }

}

