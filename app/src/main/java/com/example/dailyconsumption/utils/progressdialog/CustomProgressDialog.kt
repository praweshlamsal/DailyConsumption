package com.example.dailyconsumption.utils.progressdialog

import android.app.Activity
import android.app.Dialog
import android.widget.TextView
import com.example.dailyconsumption.R

class CustomProgressDialog(activity: Activity) {
    internal var dialog: Dialog? = null
    init {
        dialog = Dialog(activity, R.style.AppTheme_PopTheme)
    }
    fun showDialog(message: String, title: String) {

        dialog!!.setContentView(R.layout.dialog_loading)
        dialog!!.setCancelable(false)
        val tv_msg = dialog!!.findViewById(R.id.tv_msg) as TextView
        val tv_title = dialog!!.findViewById(R.id.tv_title) as TextView
        tv_msg.text = message
        tv_title.text = title
        dialog!!.show()
    }

    fun dismissDialog() {
        dialog!!.dismiss()
    }


}