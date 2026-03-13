package xyz.teamgravity.navigation3result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

class ResultStore {
    private val results = mutableMapOf<Any, Any?>()

    companion object {
        val SAVER: Saver<ResultStore, Map<Any, Any?>> = Saver(
            save = { it.results.toMap() },
            restore = {
                val store = ResultStore()
                store.results.putAll(it)
                store
            }
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    @Suppress("UNCHECKED_CAST")
    fun <T> getResult(key: Any): T? {
        return results[key] as? T
    }

    fun <T> setResult(key: Any, value: T) {
        results[key] = value
    }

    fun removeResult(key: Any) {
        results.remove(key)
    }
}

@Composable
fun rememberResultStore(): ResultStore = rememberSaveable(
    saver = ResultStore.SAVER
) {
    ResultStore()
}