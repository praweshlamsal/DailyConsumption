package com.example.dailyconsumption.userlanding

import com.example.dailyconsumption.base.BaseView
import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.model.TabLandingObject

interface UserLandingContractor : BaseView {
    fun onSuccessResponse()
    fun onSuccessItemAddResponse()
    fun onListSuccess(posts:ArrayList<Post>)
    fun onErrorResponse(error:String)
    fun tabresponse(response: List<TabLandingObject>)

}