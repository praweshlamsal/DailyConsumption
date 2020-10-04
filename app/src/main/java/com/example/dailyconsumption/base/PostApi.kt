package com.example.dailyconsumption.base


import com.example.dailyconsumption.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {

    @GET("posts")
    fun getPosts(): Observable<ArrayList<Post>>
}