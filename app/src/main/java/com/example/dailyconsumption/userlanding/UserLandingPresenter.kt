package com.example.dailyconsumption.userlanding

import android.util.Log
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.BasePresenter
import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.model.TabLandingObject
import com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment.ItemListFragment
import com.example.dailyconsumption.utils.sharedprefs.SharedKeys
import com.example.dailyconsumption.utils.sharedprefs.SharedPrefsHelper
import com.example.dailyconsumption.utils.sharedprefs.SharedPrefsObject
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.ArrayList



class UserLandingPresenter(userLandingContractor: UserLandingContractor):BasePresenter<UserLandingContractor>(userLandingContractor) {
    var context = userLandingContractor.getContext()
    var sharedPrefsHelper :SharedPrefsHelper = SharedPrefsHelper()
    val posts = arrayListOf<Post>()
    private lateinit var db: FirebaseFirestore



    override fun onViewCreated() {
        var tablandingObjects = mutableListOf<TabLandingObject>()
        tablandingObjects.add(
            TabLandingObject(
                "Students",
                R.drawable.homenormal,
                R.drawable.homefill,
                ItemListFragment()
            )
        )
        tablandingObjects.add(
            TabLandingObject(
                "Students",
                R.drawable.homenormal,
                R.drawable.homefill,
                ItemListFragment()
            )
        )

        view.tabresponse(tablandingObjects)
    }

    fun requestusername():String{
        var name = sharedPrefsHelper.getSharedPreferences(context,SharedKeys.FullName,"")
        return name
    }

    fun requestuserimage():String{
        var url = sharedPrefsHelper.getSharedPreferences(context,SharedKeys.UserPhoto,"")
        return url
    }

    fun insertItemPrice(item:String,price:String){
        var userid = sharedPrefsHelper.getSharedPreferences(context,SharedKeys.UserId,"")
        db = FirebaseFirestore.getInstance()
        var new_item_add = HashMap<String,Any>()
        new_item_add.put("ItemName",item)
        new_item_add.put("ItemPrice",price)
        new_item_add.put("Userid",userid.toLong ())
        new_item_add.put("UserName",requestusername())
        db.collection("bought_item")
            .add(new_item_add)
            .addOnSuccessListener { view.onSuccessItemAddResponse() }
            .addOnFailureListener { view.onErrorResponse("Data not published") }
    }

    fun listrequest(){
        var userid = sharedPrefsHelper.getSharedPreferences(context,SharedKeys.UserId,"")
        db = FirebaseFirestore.getInstance()
        db.collection("bought_item")
            .get()
            .addOnCompleteListener { task ->
                for(document in task.getResult()!!.iterator()){
                    var item = document.get("ItemName").toString()
                    var boughtuserid = document.get("Userid").toString()
                    var price = document.get("ItemPrice").toString()
                    var username = document.get("UserName").toString()
                    posts.add(Post(boughtuserid,"45324",item,username,price))
                }
                view.onListSuccess(posts)
            }
            .addOnFailureListener {
                exception ->
            }
    }
}