package com.john.attendance.ui.fragment.matching

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.john.attendance.R
import com.john.attendance.base.BaseFragment
import com.john.attendance.base.createViewModelFactory
import com.john.attendance.data.local.SchoolDatabase
import com.john.attendance.data.local.models.Matching
import com.john.attendance.data.local.models.Student
import com.john.attendance.databinding.MatchingFragmentBinding
import com.john.attendance.ui.fragment.matching.adapter.MatchingAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class MatchingFragment : BaseFragment<MatchingFragmentBinding,MatchingViewModel,MatchingState>() {

    var studentDatSet = mutableListOf<Student>()

    override val layoutId: Int
        get() = R.layout.matching_fragment
    override val viewModelClass: Class<MatchingViewModel>
        get() = MatchingViewModel::class.java


    override fun viewModelFactory(): ViewModelProvider.Factory =
        createViewModelFactory {
            MatchingViewModel(database = context?.let {
                SchoolDatabase.getInstance(
                    it
                )
            })
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.viewState.onEach { observeState(it) }.launchIn(lifecycleScope)

        lifecycleScope.launchWhenResumed {
            viewModel.intents.send(MatchingIntents.GetDataRequest)
        }

        lifecycleScope.launchWhenResumed {
            viewModel.intents.send(MatchingIntents.GetMatchesDataRequest)
        }

    }

    override fun observeState(state: MatchingState) {
        when (state) {
            is MatchingState.Loading -> {

            }
            is MatchingState.Success<*> -> {
                if (state.data is Flow<*>) {
                    lifecycleScope.launchWhenResumed {
                        state.data.collect {
                            if (it is List<*>) {
                                studentDatSet.clear()
                                studentDatSet = (it as List<Student>).toMutableList()
                                Log.d("TAG", "observeState:${studentDatSet} ")
                                if (studentDatSet.isNotEmpty()) {
                                    var i = 0
                                    studentDatSet.forEach {
                                        var radmIndex = Random.nextInt(i, studentDatSet.size)
                                        if (radmIndex == i) {
                                            radmIndex = Random.nextInt(i, studentDatSet.size)
                                        }
                                        viewModel.intents.send(
                                            MatchingIntents.AddMatch(
                                                Matching(
                                                    it.StudentID!!, studentDatSet[radmIndex].StudentID!!
                                                )
                                            )
                                        )
                                        i++
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is MatchingState.SuccessMatch<*>->{
                val list = arrayListOf<MatchStudent>()
                var adapter=MatchingAdapter()
                if (state.data is Flow<*>) {
                    lifecycleScope.launchWhenResumed {
                        state.data.collect {
                            if (it is List<*>) {
                                val MatchDatSet = (it as List<Matching>).toMutableList()
                                list.clear()
                                MatchDatSet.forEach {
                                  list.add(MatchStudent( studentDatSet.filter {them->them.StudentID==it.studentIDHome}.get(0).name.toString(),studentDatSet.filter {them->them.StudentID==it.studentIDWay}.get(0).name.toString()))
                                }
                                Log.d("TAG", "observeStatematch: ${list}")
                                    binding.rvItemlist.adapter = adapter
                                    adapter.addItems(list)
                            }
                        }
                    }
                }

            }
        }
    }
}