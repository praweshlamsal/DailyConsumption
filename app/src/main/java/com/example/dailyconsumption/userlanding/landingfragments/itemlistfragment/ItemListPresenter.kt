package com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment

import android.app.Activity
import android.util.Log
import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.model.UserDetail
import com.example.dailyconsumption.utils.sharedprefs.SharedPrefsHelper
import com.google.firebase.firestore.FirebaseFirestore

class ItemListPresenter : ItemListContractor.Presenter {

    var sharedPrefsHelper: SharedPrefsHelper = SharedPrefsHelper()
    val posts = arrayListOf<Post>()
    val userDetails = arrayListOf<UserDetail>()
    private lateinit var db: FirebaseFirestore
    var activity: Activity
    var view: ItemListContractor.View

    constructor(activity: Activity, view: ItemListContractor.View) {
        this.activity = activity
        this.view = view
    }


    override fun onListResponse() {
        userDetails.clear()
        posts.clear()
        db = FirebaseFirestore.getInstance()
        db.collection("bought_item")
            .get()
            .addOnCompleteListener { task ->
                for (document in task.getResult()!!.iterator()) {
                    var item = document.get("ItemName").toString()
                    var boughtuserid = document.get("Userid").toString()
                    var price = document.get("ItemPrice").toString()
                    var username = document.get("UserName").toString()
                    posts.add(Post(boughtuserid, "45324", item, username, price))
                }
                Log.d("okayman",posts.size.toString())
                view.onUserPostSuccess(posts)
            }
            .addOnFailureListener { exception ->
                view.onError("Item Loading Failed.")
            }

        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                for (document in task.getResult()!!.iterator()) {
                    var email = document.get("email").toString()
                    var fullName = document.get("fullname").toString()
                    var profileImage = document.get("photo").toString()
                    userDetails.add(UserDetail(email, fullName, profileImage))
                }
                view.onUserListSuccess(userDetails)
            }
            .addOnFailureListener { exception ->
                view.onError("User Loading Failed.")
            }
    }

}