package com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment

import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.model.UserDetail

interface ItemListContractor {
    interface View{
        fun onUserListSuccess(userdetail:ArrayList<UserDetail>)
        fun onUserPostSuccess(post:ArrayList<Post>)
        fun onError(error: String)
    }

    interface Presenter{
        fun onListResponse()
    }
}