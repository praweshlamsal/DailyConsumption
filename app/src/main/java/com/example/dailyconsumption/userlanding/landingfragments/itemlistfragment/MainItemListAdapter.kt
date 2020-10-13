package com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.BaseViewHolder
import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment.parentviews.itemview.ItemListView

class MainItemListAdapter(val userPost: ArrayList<Post>, private val context: Context) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false)
        return ItemListView(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = userPost[position]
        when(holder){
            is ItemListView -> {
                holder.bind(element)
            }
            else-> throw IllegalArgumentException("Invalid view holder")
        }
    }

    override fun getItemCount(): Int {
        return userPost.size
    }


}