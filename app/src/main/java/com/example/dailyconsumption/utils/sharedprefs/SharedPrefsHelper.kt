package com.example.dailyconsumption.utils.sharedprefs

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.dailyconsumption.BuildConfig

public class SharedPrefsHelper{
    var PREFS_NAME = BuildConfig.APPLICATION_ID

    var LANGUAGE_SETTING = "languageSettings"


    fun getSharedPreferences(context: Context, key: String, defaultValue: String): String {
        val settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return settings.getString(key, defaultValue)!!
    }

    fun getSharedPreferences(context: Activity, key: String, defaultValue: String): String {
        val settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return settings.getString(key, defaultValue)!!
    }

    fun setSharedPreferences(context: Context, key: String, value: String): Boolean {
        val settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        try {
            val editor = settings.edit()
            editor.putString(key, value)
            editor.commit()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }


    fun setSharedPreferences(context: Activity, mlist: List<SharedPrefsObject>): Boolean {
        val settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        try {
            val editor = settings.edit()
            for (i in mlist.indices) {
                editor.putString(mlist[i].key, mlist[i].value)
            }
            editor.apply()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }


    fun setSharedPreferences(context: Activity, key: String, value: String): Boolean {
        val settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        try {
            val editor = settings.edit()
            editor.putString(key, value)
            editor.commit()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun clearAll(context: Context) {
        val settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        settings.edit().clear().commit()
    }
}