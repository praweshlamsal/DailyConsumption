package com.example.dailyconsumption.login

import com.example.dailyconsumption.base.BaseView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface LoginContractor : BaseView {

    fun loginUser()

    fun loginAdmin()

    fun loginSuccess()

    fun ShowError(error: String)

    fun showLoading()

    fun hideLoading()
}