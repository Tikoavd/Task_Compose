package com.task_compose.mvi

interface Reducer<A : MviAction, S : MviState> {

    fun reduce(action: A, state: S): S
}
