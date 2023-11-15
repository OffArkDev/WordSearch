package offarkdev.wordssearch.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import offarkdev.wordssearch.ui.root.RootComponent.Child
import kotlinx.parcelize.Parcelize
import offarkdev.wordssearch.ui.root.DefaultRootComponent.Config.Details
import offarkdev.wordssearch.ui.root.DefaultRootComponent.Config.Loading
import offarkdev.wordssearch.ui.root.DefaultRootComponent.Config.Search
import offarkdev.wordssearch.ui.details.DefaultDetailsScreenComponent
import offarkdev.wordssearch.ui.details.DetailsScreenComponent
import offarkdev.wordssearch.ui.details.WordData
import offarkdev.wordssearch.ui.loading.DefaultLoadDataScreenComponent
import offarkdev.wordssearch.ui.loading.LoadDataScreenComponent
import offarkdev.wordssearch.ui.search.DefaultSearchScreenComponent
import offarkdev.wordssearch.ui.search.SearchScreenComponent


class DefaultRootComponent(
    private val componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        initialConfiguration = Loading,
        handleBackButton = true,
        childFactory = ::child,
    )


    private fun child(config: Config, childComponentContext: ComponentContext): Child {
        return when (config) {
            Search -> Child.Search(searchComponent(childComponentContext))
            is Details -> Child.Details(detailsComponent(childComponentContext, config.data))
            Loading -> Child.Loading(loadingComponent(childComponentContext))
        }
    }

    private fun searchComponent(componentContext: ComponentContext): SearchScreenComponent =
        DefaultSearchScreenComponent(
            componentContext = componentContext,
            onInput = { data -> navigation.push(Details(data)) }
        )

    private fun detailsComponent(componentContext: ComponentContext, data: List<WordData>): DetailsScreenComponent =
        DefaultDetailsScreenComponent(
            componentContext = componentContext,
            data = data
        )

    private fun loadingComponent(componentContext: ComponentContext): LoadDataScreenComponent =
        DefaultLoadDataScreenComponent(
            componentContext = componentContext,
            onLoaded = { navigation.replaceCurrent(Search) }
        )


    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data class Details(val data: List<WordData>) : Config

        @Parcelize
        data object Search : Config

        @Parcelize
        data object Loading : Config

    }
}
