package com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment.parentviews.itemview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.BaseViewHolder
import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.model.UserDetail

class ItemListView(itemView: View) : BaseViewHolder<Post>(itemView) {

    var item_title: TextView
    var item_body: TextView
    var item_price: TextView

    init {
        item_title = itemView.findViewById(R.id.post_title)
        item_body = itemView.findViewById(R.id.post_body)
        item_price = itemView.findViewById(R.id.post_price)
    }
    override fun bind(item: Post) {
        item_title.setText(item.item)
        item_body.setText(item.user)
        item_price.setText(item.price)
    }
}