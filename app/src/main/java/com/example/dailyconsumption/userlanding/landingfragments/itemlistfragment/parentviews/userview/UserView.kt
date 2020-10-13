package com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment.parentviews.userview

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.BaseViewHolder
import com.example.dailyconsumption.model.UserDetail
import de.hdodenhof.circleimageview.CircleImageView

class UserView(itemView: View) : BaseViewHolder<UserDetail>(itemView){
    var user_image: CircleImageView
    var user_name: TextView

    init {
        user_image = itemView.findViewById(R.id.user_img)
        user_name = itemView.findViewById(R.id.user_name)
    }

    override fun bind(item: UserDetail) {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
        Glide.with(itemView.context).load(item.photo).apply(options)
            .into(user_image)
        var namearray = item.fullname.split(" ").toTypedArray()
        user_name.setText(namearray[0])
    }
}