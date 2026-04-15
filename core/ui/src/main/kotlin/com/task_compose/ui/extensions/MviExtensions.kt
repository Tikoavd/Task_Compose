package com.task_compose.ui.extensions

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.task_compose.mvi.MviAction
import com.task_compose.mvi.MviBaseViewModel
import com.task_compose.mvi.MviEffect
import com.task_compose.mvi.MviIntent
import com.task_compose.mvi.MviState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@SuppressLint("ComposableNaming")
@Composable
fun <S : MviState, A : MviAction, I : MviIntent, E : MviEffect>
        MviBaseViewModel<S, A, I, E>.collectEffects(
    lifecycleState: Lifecycle.State = androidx.lifecycle.Lifecycle.State.STARTED,
    sideEffect: (suspend CoroutineScope.(effect: E) -> Unit)
) {
    val effectsFlow = effects
    val lifecycleOwner = LocalLifecycleOwner.current

    val callback by rememberUpdatedState(newValue = sideEffect)

    LaunchedEffect(effectsFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            effectsFlow.collect { callback(it) }
        }
    }
}
