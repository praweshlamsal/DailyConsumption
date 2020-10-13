package com.example.dailyconsumption.model

import io.realm.RealmObject


open class Post(
    var userId: String = "",
    var id: String = "",
    var item: String = "",
    var user: String = "",
    var price:String= ""):RealmObject()