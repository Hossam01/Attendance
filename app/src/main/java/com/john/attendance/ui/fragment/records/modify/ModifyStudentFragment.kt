package com.john.attendance.ui.fragment.records.modify

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.john.attendance.data.local.models.Student
import com.john.attendance.databinding.FragmentModifyStudentBinding
import com.john.attendance.ui.fragment.records.AttendanceRecordsViewModel
import com.john.attendance.util.callback.OnDialogCallback

class ModifyStudentFragment : DialogFragment() {

    companion object {
        fun newInstance() = ModifyStudentFragment()
        val TAG = this.javaClass.simpleName
    }

    val viewModel: AttendanceRecordsViewModel by activityViewModels()
    private lateinit var binding: FragmentModifyStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModifyStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    var onDialogCallback: OnDialogCallback<Pair<Student, Int>>? = null
    private var student: Student? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (arguments?.isEmpty == true)
            student = Student()
        else
            arguments?.getParcelable<Student>("student")?.let {
                student = it
            }
        binding.student = student
        binding.executePendingBindings()
        binding.deleteBtn.setOnClickListener {
            passDataToFragment(3)
        }

        binding.confirmBtn.setOnClickListener {
            passDataToFragment(if (student == null) 1 else 2)
        }
    }

    private fun passDataToFragment(action: Int) {
        onDialogCallback?.pass(
            Pair(
                Student(
                    StudentID = student?.StudentID,
                    name = binding.studentNameEdt.text.toString().trim(),
                    attendanceTimes = binding.attendanceNumberEdt.text?.toString()?.trim()
                        ?.toInt() ?: 0,
                    absenceTimes = binding.absenceNumberEdt.text?.toString()?.trim()?.toInt()
                        ?: 0,
                ), action
            )
        )
        this.dismiss()
    }
}