package xyz.teamgravity.navigation3result

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun Navigation() {
    val stack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Receiver::class, Route.Receiver.serializer())
                    subclass(Route.Sender::class, Route.Sender.serializer())
                }
            }
        },
        Route.Receiver
    )
    val store = rememberResultStore()
    NavDisplay(
        backStack = stack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Receiver> {
                val result = store.getResult<String>(Route.Receiver.EXTRA_RESULT) ?: ""
                ReceiverScreen(
                    result = result,
                    onNavigateSender = { result ->
                        stack.add(Route.Sender(result))
                    }
                )
            }
            entry<Route.Sender> { route ->
                SenderScreen(
                    initialText = route.initialText,
                    onNavigateBack = { result ->
                        store.setResult(Route.Receiver.EXTRA_RESULT, result)
                        stack.removeLastOrNull()
                    }
                )
            }
        }
    )
}