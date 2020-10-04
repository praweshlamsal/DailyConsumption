package com.example.dailyconsumption.utils.yesnodialog

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.dailyconsumption.R
import kotlinx.android.synthetic.main.dialog_yes_no.view.*

class CustomYesNoDialog
    (activity:Activity,
     title:String,
     message:String,
     yesString: String,
     noString: String,
     yesNoAction: YesNoAction) {
    internal var dialog: Dialog? = null
    internal var yesNoAction: YesNoAction? = null

    init{
        dialog = Dialog(activity, R.style.AppTheme_PopTheme)
        dialog!!.setContentView(R.layout.dialog_yes_no)
        dialog!!.setCancelable(false)
        this.yesNoAction = yesNoAction
        var card_yes = dialog!!.findViewById<CardView>(R.id.card_yes)
        var tv_yes = dialog!!.findViewById<TextView>(R.id.tv_yes)
        var card_no =dialog!!.findViewById<CardView>(R.id.card_no)
        var tv_no = dialog!!.findViewById<TextView>(R.id.tv_no)
        var tv_msg = dialog!!.findViewById<TextView>(R.id.tv_msg)
        var tv_title = dialog!!.findViewById<TextView>(R.id.tv_title)
        tv_yes.setText(yesString)
        tv_no.setText(noString)
        tv_msg.setText(message)
        tv_title.setText(title)
        card_yes.setOnClickListener(View.OnClickListener {
            yesNoAction.onYes()
            dialog!!.dismiss()
        })

        card_no.setOnClickListener(View.OnClickListener {
            yesNoAction.onNo()
            dialog!!.dismiss()
        })
        dialog!!.show()
    }

}