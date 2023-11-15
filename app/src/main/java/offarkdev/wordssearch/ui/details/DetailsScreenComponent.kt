package offarkdev.wordssearch.ui.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value


class DefaultDetailsScreenComponent(
    private val componentContext: ComponentContext,
    data: List<WordData>
) : DetailsScreenComponent, ComponentContext by componentContext {


    private val state = MutableValue(data)
    override val model: Value<List<WordData>> = state
}



interface DetailsScreenComponent {

    val model: Value<List<WordData>>
}