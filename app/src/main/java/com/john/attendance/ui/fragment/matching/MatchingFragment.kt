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
import java.lang.Exception
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class MatchingFragment : BaseFragment<MatchingFragmentBinding,MatchingViewModel,MatchingState>() {

    var studentDatSet = mutableSetOf<Student>()

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

        binding.refresh.setOnClickListener {

            lifecycleScope.launchWhenResumed {
                viewModel.intents.send(MatchingIntents.RemoveStudent)
            }
            lifecycleScope.launchWhenResumed {
                viewModel.intents.send(MatchingIntents.GetDataRequest)
            }
            lifecycleScope.launchWhenResumed {
                viewModel.intents.send(MatchingIntents.GetMatchesDataRequest)
            }
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
                                studentDatSet = (it as List<Student>).toMutableSet()
                                Log.d("TAG", "observeState:${studentDatSet.size} ")
                                if (studentDatSet.isNotEmpty()) {
                                    binding.rvItemlist.visibility=View.VISIBLE
                                    binding.studentHome.visibility=View.GONE
                                    var i = 0


//                                    var length:Int=(studentDatSet.size / 2)
//                                    for (i in 0..length)
//                                    {
//                                        viewModel.intents.send(
//                                            MatchingIntents.AddMatch(
//                                                Matching(
//                                                    studentDatSet.elementAt(i).StudentID!!,
//                                                    studentDatSet.elementAt((length-1)+i).StudentID!!
//                                                )
//                                            )
//                                        )
//                                    }


                                    if (studentDatSet.size % 2 != 0) {
                                        for (i in 0..studentDatSet.size -2) {
                                            var radmIndex = Random.nextInt(i, studentDatSet.size)
                                            if (radmIndex != i) {
                                                viewModel.intents.send(
                                                    MatchingIntents.AddMatch(
                                                        Matching(
                                                            studentDatSet.elementAt(i).StudentID!!,
                                                            studentDatSet.elementAt(radmIndex).StudentID!!
                                                        )
                                                    )
                                                )
                                            }
                                        }
                                    } else {
                                        studentDatSet.forEach {
                                            var radmIndex = Random.nextInt(i, studentDatSet.size)
                                            if (radmIndex != i) {
                                                viewModel.intents.send(
                                                    MatchingIntents.AddMatch(
                                                        Matching(
                                                            it.StudentID!!,
                                                            studentDatSet.elementAt(radmIndex).StudentID!!
                                                        )
                                                    )
                                                )
                                            }
                                            i++
                                        }
                                    }
                                }
                                else
                                {
                                    binding.rvItemlist.visibility=View.INVISIBLE
                                    binding.studentHome.visibility=View.VISIBLE
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
                                try {


                                    MatchDatSet.forEach {
                                        list.add(
                                            MatchStudent(
                                                studentDatSet.filter { them -> them.StudentID == it.studentIDHome }
                                                    .get(0).name.toString(),
                                                studentDatSet.filter { them -> them.StudentID == it.studentIDWay }
                                                    .get(0).name.toString()
                                            )
                                        )
                                    }
                                }catch (e:Exception){
                                    lifecycleScope.launchWhenResumed {
                                        viewModel.intents.send(MatchingIntents.GetDataRequest)
                                    }
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