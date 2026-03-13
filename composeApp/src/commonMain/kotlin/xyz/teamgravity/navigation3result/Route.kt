package xyz.teamgravity.navigation3result

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Receiver : Route {
       const val EXTRA_RESULT = "Receiver.extra.result"
    }

    @Serializable
    data class Sender(
        val initialText: String
    ) : Route
}