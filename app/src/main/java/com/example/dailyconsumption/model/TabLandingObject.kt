package com.example.dailyconsumption.model

import androidx.fragment.app.Fragment
import io.realm.RealmObject

open class TabLandingObject (var title: String,
                             var normal: Int,
                             var bold: Int,
                             var fragment: Fragment)

