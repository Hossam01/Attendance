package com.john.attendance.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner


abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel, VS : ViewState> : Fragment(),
    StateHandler<VS> {
    protected lateinit var binding: DB
    open lateinit var viewModel: VM

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: Class<VM>


    protected abstract fun viewModelFactory(): ViewModelProvider.Factory

    protected open fun viewModelStoreOwner(): ViewModelStoreOwner = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel =
            ViewModelProvider(viewModelStoreOwner(), viewModelFactory())[this.viewModelClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, this.layoutId, container, false)
        this.binding.lifecycleOwner = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

//        getSig("SHA1")

        return this.binding.root
    }


    companion object {
        val fragment: MutableLiveData<String> = MutableLiveData()
        const val GO_TO_MONEY = 8599
    }

    fun isBindingInitialized() = ::binding.isInitialized
}