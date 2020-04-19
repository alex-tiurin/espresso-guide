package com.atiurin.espressoguide.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.core.settings.SettingsItem

class SettingsAdapter(private var dataset: List<SettingsItem>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<SettingsAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: SettingsItem)
    }
    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.settings_list_item, parent, false) as LinearLayout
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.setOnClickListener { listener.onItemClick(dataset.get(position)) }
        val tvTitle = holder.view.findViewById(R.id.tv_name) as TextView
        tvTitle.text = dataset[position].name
    }

    override fun getItemCount() = dataset.size
}