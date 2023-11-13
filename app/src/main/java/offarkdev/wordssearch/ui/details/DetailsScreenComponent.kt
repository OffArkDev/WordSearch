package offarkdev.wordssearch.ui.details

import com.arkivanov.decompose.ComponentContext


class DefaultDetailsScreenComponent(
    private val componentContext: ComponentContext,
) : DetailsScreenComponent, ComponentContext by componentContext {}



interface DetailsScreenComponent