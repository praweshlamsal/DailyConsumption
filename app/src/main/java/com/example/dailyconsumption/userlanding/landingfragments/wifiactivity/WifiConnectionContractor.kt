package com.example.dailyconsumption.userlanding.landingfragments.wifiactivity

import com.example.dailyconsumption.base.BaseView

interface WifiConnectionContractor:BaseView {
    fun loadUsers()
    fun showLoading()
    fun hideLoading()
}