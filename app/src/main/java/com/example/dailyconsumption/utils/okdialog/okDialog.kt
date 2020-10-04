package com.example.dailyconsumption.utils.okdialog

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.dailyconsumption.R

class okDialog{
    internal var dialog: Dialog? = null

    fun okDialog(message: String, activity: Activity) {
        dialog = Dialog(activity, R.style.AppTheme_PopTheme)
        dialog!!.setContentView(R.layout.dialog_ok)
        val tv_msg = dialog!!.findViewById(R.id.tv_msg) as TextView
        val ok = dialog!!.findViewById(R.id.btn_ok) as Button

        tv_msg.text = message

        ok.setOnClickListener { dialog!!.dismiss() }

        dialog!!.show()

    }
}