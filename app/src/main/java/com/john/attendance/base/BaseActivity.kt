package com.john.attendance.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel>
    : AppCompatActivity() {
    lateinit var binding: DB
    protected abstract val layoutId: Int
    protected lateinit var viewModel: VM

    protected abstract val viewModelClass: Class<VM>

    protected abstract fun viewModelFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory())[this.viewModelClass]
        binding = DataBindingUtil.setContentView(this, this.layoutId)
    }
}