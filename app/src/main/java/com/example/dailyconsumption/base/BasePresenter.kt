package com.example.dailyconsumption.base

import com.example.dailyconsumption.injection.component.DaggerPresenterInjector
import com.example.dailyconsumption.injection.component.PresenterInjector
import com.example.dailyconsumption.injection.module.ContextModule
import com.example.dailyconsumption.injection.module.NetworkModule
import com.example.dailyconsumption.login.LoginPresenter


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
        }
    }
}