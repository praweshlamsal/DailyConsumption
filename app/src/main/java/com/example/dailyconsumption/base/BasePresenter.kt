package com.example.dailyconsumption.base

import com.example.dailyconsumption.injection.component.DaggerPresenterInjector
import com.example.dailyconsumption.injection.component.PresenterInjector
import com.example.dailyconsumption.injection.module.ContextModule
import com.example.dailyconsumption.injection.module.NetworkModule
import com.example.dailyconsumption.login.LoginPresenter
import com.example.dailyconsumption.userlanding.UserLandingPresenter
import com.example.dailyconsumption.userlanding.landingfragments.wifiactivity.WifiConnectionPresenter


abstract class BasePresenter<out V : BaseView>(protected val view: V) {


    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .build()


    init {
        inject()
    }

    open fun onViewCreated() {}


    open fun onViewDestroyed() {}


    private fun inject() {
        when(this){
            is LoginPresenter -> injector.injectLogin(this)
            is UserLandingPresenter ->injector.injectUserLanding(this)
            is WifiConnectionPresenter -> injector.injectWifiConnection(this)
        }
    }
}