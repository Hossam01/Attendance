package com.john.attendance.ui.fragment.records.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.attendance.data.local.models.Student
import com.john.attendance.databinding.ItemListStudentsBinding
import com.john.attendance.util.callback.OnItemAdapterClicked


class StudentListAdapter(
    var student: MutableList<Student> = emptyList<Student>().toMutableList(),
    private val sendClick: OnItemAdapterClicked<Student>,
) : RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            ItemListStudentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return student.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tab = student[position]
        holder.bindTab(position, tab)
    }


    inner class ViewHolder(private val item: ItemListStudentsBinding) :
        RecyclerView.ViewHolder(item.root) {
        internal fun bindTab(position: Int, student: Student) {
            item.studnet = student
            item.number = position
            item.root.setOnClickListener {
                sendClick.onClickListener(student)
            }
        }
    }

    fun addToList(items: List<Student>) {
        student = items.toMutableList()
        notifyDataSetChanged()
    }
}