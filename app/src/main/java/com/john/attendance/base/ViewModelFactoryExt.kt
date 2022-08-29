package com.john.attendance.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(noinline viewModelBlock: () -> T) =
    ViewModelProvider(this, ViewModelFactory { viewModelBlock() }).get(T::class.java)

inline fun <reified T : ViewModel> ViewModelStoreOwner.createViewModelFactory(noinline viewModelBlock: () -> T) =
    ViewModelFactory { viewModelBlock() }