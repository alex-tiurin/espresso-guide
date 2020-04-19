package com.atiurin.espressoguide.adapters

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.view.CircleImageView

class ContactAdapter(private var contacts: List<Contact>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {
    private val contactPosition = mutableMapOf<Contact, View>()
    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
        fun onItemLongClick(contact: Contact)
    }

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view)

    open fun updateData(data: List<Contact>) {
        contacts = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friends_list_item, parent, false) as LinearLayout
        val viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.setOnClickListener { listener.onItemClick(contacts[position]) }
        holder.view.setOnLongClickListener {
            listener.onItemLongClick(contacts[position])
            true
        }
        val tvTitle = holder.view.findViewById(R.id.tv_name) as TextView
        val avatar = holder.view.findViewById(R.id.avatar) as CircleImageView
        val status = holder.view.findViewById(R.id.tv_status) as TextView
        tvTitle.text = contacts[position].name
        status.text = contacts[position].status
        avatar.setImageDrawable(holder.view.context.resources.getDrawable(contacts[position].avatar))
        contactPosition[contacts[position]] =  holder.view
    }

    fun getContactView(contact: Contact): View {
        return contactPosition[contact]!!
    }
    override fun getItemCount() = contacts.size
}