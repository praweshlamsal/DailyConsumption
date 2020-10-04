package com.example.dailyconsumption.base.bottomsheetdrawer

import android.app.Dialog
import android.os.Bundle
import com.example.dailyconsumption.base.BasePresenter
import com.example.dailyconsumption.base.BaseView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<P : BasePresenter<BaseView>>:BottomSheetDialogFragment() {
    protected lateinit var presenter: P

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        presenter = instantiatePresenter()
        return super.onCreateDialog(savedInstanceState)
    }

    protected abstract fun instantiatePresenter():P
}