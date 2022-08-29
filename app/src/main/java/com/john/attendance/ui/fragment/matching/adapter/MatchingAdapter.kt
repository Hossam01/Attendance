package com.john.attendance.ui.fragment.matching.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.attendance.data.local.models.Student
import com.john.attendance.databinding.MatchItemBinding
import com.john.attendance.ui.fragment.matching.MatchStudent

class MatchingAdapter : RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {

    private val dataSet: MutableList<MatchStudent> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingAdapter.ViewHolder {
        return ViewHolder(
            MatchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchingAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
    fun addItems(items: MutableList<MatchStudent>) {
        dataSet.clear()
        dataSet.addAll(items)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = dataSet.size

    inner class ViewHolder(private val view: MatchItemBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(item: MatchStudent) = with(view) {
            view.studentHome.text=item.studentHome
            view.studentWay.text=item.studentWay
        }
    }

}