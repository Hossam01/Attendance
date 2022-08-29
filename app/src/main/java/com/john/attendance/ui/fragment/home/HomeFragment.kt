package com.john.attendance.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.john.attendance.R
import com.john.attendance.base.BaseFragment
import com.john.attendance.base.createViewModelFactory
import com.john.attendance.databinding.FragmentHomeBinding
import com.john.attendance.ui.fragment.login.LoginViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel,HomeState>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        createViewModelFactory { HomeViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recordBtn.setOnClickListener {
            findNavController().navigate(R.id.attendanceRecordsFragment)
        }
        binding.matchingBtn.setOnClickListener {
            findNavController().navigate(R.id.matchingFragment)
        }
    }

    override fun observeState(state: HomeState) {

    }
}