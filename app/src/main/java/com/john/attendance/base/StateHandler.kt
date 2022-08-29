package com.john.attendance.base

interface StateHandler<T> {
    fun observeState(state: T)
}