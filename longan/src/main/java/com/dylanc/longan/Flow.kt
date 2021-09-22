@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author Dylan Cai
 */

inline fun MutableSharedFlow() = MutableSharedFlow<Unit>()

suspend inline fun MutableSharedFlow<Unit>.emit() = emit(Unit)

inline fun MutableSharedFlow<Unit>.tryEmit() = tryEmit(Unit)

inline fun <T> Flow<T>.launchAndCollectIn(
  owner: LifecycleOwner,
  minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
  crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
  owner.repeatOnLifecycle(minActiveState) {
    collect {
      action(it)
    }
  }
}
