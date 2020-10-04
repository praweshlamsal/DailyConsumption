package com.example.dailyconsumption.base.basefragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailyconsumption.utils.sharedprefs.SharedPrefsHelper

public abstract class BaseFragment:Fragment() {
    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(myLayout(), container, false)
        activityCreated()
        return rootView
    }

    abstract fun getActivityContext(): Activity?

    abstract fun myLayout(): Int

    abstract fun activityCreated()

}