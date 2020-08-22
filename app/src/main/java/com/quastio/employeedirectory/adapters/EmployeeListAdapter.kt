package com.quastio.employeedirectory.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.quastio.employeedirectory.R
import com.quastio.employeedirectory.models.EmployeeDbModel

class EmployeeListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EmployeeDbModel>() {

        override fun areItemsTheSame(oldItem: EmployeeDbModel, newItem: EmployeeDbModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EmployeeDbModel,
            newItem: EmployeeDbModel
        ): Boolean {
            return  oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return EmployeeviewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.employee_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmployeeviewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<EmployeeDbModel>) {
        differ.submitList(list)
    }

    class EmployeeviewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: EmployeeDbModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            TODO("bind view with data")
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: EmployeeDbModel)
    }
}

