package com.atiurin.espressoguide

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atiurin.espressoguide.data.Contact

class RecyclerAdapter(private val myDataset: ArrayList<Contact>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerAdapter.MyViewHolder {
        // create a new view

           val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as LinearLayout



        // set the view's size, margins, paddings and layout parameters .
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val tvTitle = holder.view.findViewById(R.id.tv_title) as TextView
        val avatar = holder.view.findViewById(R.id.avatar) as CircleImageView
        tvTitle.text = myDataset[position].name
        avatar.setImageDrawable(getDrawableByName(myDataset[position].avatar, holder.view.context))

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    fun getDrawableByName(name: String, context: Context) : Drawable{
        val resources = context.getResources()
        val resourceId = resources.getIdentifier(
            name, "drawable",
            context.getPackageName()
        )
        return resources.getDrawable(resourceId)
    }
}