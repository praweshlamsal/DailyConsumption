package com.example.dailyconsumption.userlanding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.dailyconsumption.R
import com.example.dailyconsumption.utils.okdialog.okDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddItemBottomSheetFragment(userLandingPresenter: UserLandingPresenter): BottomSheetDialogFragment() {
    lateinit var itemName_et: EditText
    lateinit var itemPrice_et: EditText
    lateinit var valueEnter_btn: Button
    lateinit var customdialog:okDialog
    var presenter = userLandingPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{
       val view =inflater.inflate(R.layout.add_item_fragment, container, false)
        itemName_et = view.findViewById<EditText>(R.id.etItemName)
        itemPrice_et = view.findViewById<EditText>(R.id.etItemPrice)
        valueEnter_btn = view.findViewById<Button>(R.id.valueEnter)
        customdialog = okDialog()
        valueEnter_btn.setOnClickListener {
            if(!itemName_et.text.toString().equals("") || !itemName_et.text.toString().equals("") ){
                presenter.insertItemPrice(itemName_et.text.toString(),itemPrice_et.text.toString())
            }
            else if(itemName_et.text.toString().equals("") || itemPrice_et.text.toString().equals("")){

                customdialog.okDialog("Please add item name",this.requireActivity())
                itemName_et.requestFocus()
            }
        }
        return view
    }


}