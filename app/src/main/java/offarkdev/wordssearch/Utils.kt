package offarkdev.wordssearch

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.plus
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent
import kotlin.coroutines.CoroutineContext


inline fun <reified T> inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> {
    return KoinJavaComponent.inject(T::class.java, qualifier, parameters)
}


private fun CoroutineScope(context: CoroutineContext, lifecycle: Lifecycle): CoroutineScope {
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}

fun ComponentContext.coroutineScope(): CoroutineScope =
    CoroutineScope(Dispatchers.Main.immediate, lifecycle) + SupervisorJob()