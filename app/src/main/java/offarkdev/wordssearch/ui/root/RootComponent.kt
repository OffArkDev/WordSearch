package offarkdev.wordssearch.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import offarkdev.wordssearch.ui.details.DetailsScreenComponent
import offarkdev.wordssearch.ui.details.WordData
import offarkdev.wordssearch.ui.loading.LoadDataScreenComponent
import offarkdev.wordssearch.ui.search.SearchScreenComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>


    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Search(val component: SearchScreenComponent) : Child()
        class Loading(val component: LoadDataScreenComponent) : Child()
        class Details(val data: List<WordData>, val component: DetailsScreenComponent) : Child()
    }
}