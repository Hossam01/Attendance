package com.john.attendance.ui.fragment.records

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.attendance.R
import com.john.attendance.base.BaseFragment
import com.john.attendance.base.createViewModelFactory
import com.john.attendance.data.local.SchoolDatabase
import com.john.attendance.data.local.models.Matching
import com.john.attendance.data.local.models.Student
import com.john.attendance.databinding.FragmentAttendanceRecordsBinding
import com.john.attendance.ui.fragment.home.HomeFragment
import com.john.attendance.ui.fragment.matching.MatchingIntents
import com.john.attendance.ui.fragment.records.adapter.StudentListAdapter
import com.john.attendance.ui.fragment.records.modify.ModifyStudentFragment
import com.john.attendance.util.callback.OnDialogCallback
import com.john.attendance.util.callback.OnItemAdapterClicked
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.random.Random

class AttendanceRecordsFragment :
    BaseFragment<FragmentAttendanceRecordsBinding, AttendanceRecordsViewModel, RecordsState>(),
    OnItemAdapterClicked<Student>, OnDialogCallback<Pair<Student, Int>> {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_attendance_records
    override val viewModelClass: Class<AttendanceRecordsViewModel>
        get() = AttendanceRecordsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        createViewModelFactory {
            AttendanceRecordsViewModel(database = context?.let {
                SchoolDatabase.getInstance(
                    it
                )
            })
        }

    private val itemsAdapter: StudentListAdapter = StudentListAdapter(sendClick = this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvStudents.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.adapter = itemsAdapter
        binding.rvStudents.adapter = itemsAdapter

        viewModel.viewState.onEach { observeState(it) }.launchIn(lifecycleScope)
        binding.addStudentButton.setOnClickListener {
            ModifyStudentFragment()
                .apply {
                    onDialogCallback = this@AttendanceRecordsFragment
                }.also {
                    it.show(childFragmentManager, ModifyStudentFragment.TAG)
                }
        }

        lifecycleScope.launch {
            viewModel.intents.send(RecordsIntents.GetDataRequest)
        }
    }

    override fun observeState(state: RecordsState) {
        when (state) {
            is RecordsState.Loading -> {

            }
            is RecordsState.Success<*> -> {
                if (state.data is Flow<*>) {
                    lifecycleScope.launch {
                        state.data.collect {
                            if (it is List<*>) {
                                val studentDatSet = (it as List<Student>).toMutableList()
                                if (studentDatSet.isNotEmpty()) {
                                    itemsAdapter.addToList(it)
                                    activity?.runOnUiThread {
                                        itemsAdapter.notifyDataSetChanged()
                                        binding.idTeamsGrid.visibility = View.VISIBLE
                                        binding.emptyDataTxt.visibility = View.GONE
                                    }
                                } else {
                                    activity?.runOnUiThread {
                                        binding.idTeamsGrid.visibility = View.GONE
                                        binding.emptyDataTxt.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onClickListener(item: Student?) {
        item?.let { item ->
            ModifyStudentFragment()
                .apply {
                    arguments = Bundle().apply {
                        this.putParcelable("student", item)
                    }
                    onDialogCallback = this@AttendanceRecordsFragment
                }.also {
                    it.show(childFragmentManager, ModifyStudentFragment.TAG)
                }
        }
    }

    override fun pass(item: Pair<Student, Int>?) {
        item?.let { _item ->
            lifecycleScope.launch {
                viewModel.intents.send(
                    when (item.second) {
                        1 -> RecordsIntents.AddStudent(item.first)
                        2 -> RecordsIntents.UpdateStudent(item.first)
                        else -> RecordsIntents.RemoveStudent(item.first)
                    }
                )
            }
        }
    }
}