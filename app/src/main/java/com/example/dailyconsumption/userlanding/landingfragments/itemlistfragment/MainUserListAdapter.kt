package com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.BaseViewHolder
import com.example.dailyconsumption.model.UserDetail
import com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment.parentviews.userview.UserView

class MainUserListAdapter(val userListDetail: ArrayList<UserDetail>, private val context: Context) : RecyclerView.Adapter<BaseViewHolder<*>>() {


    fun clearList(){
        userListDetail.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false)
        return UserView(view)
    }

    override fun getItemCount(): Int {
        return userListDetail.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = userListDetail[position]
        when(holder){
            is UserView -> {
                holder.bind(element)
            }
            else-> throw IllegalArgumentException("Invalid view holder")
        }
    }

}