package com.example.dailyconsumption.injection.component

import com.example.dailyconsumption.base.BaseView
import com.example.dailyconsumption.injection.module.ContextModule
import com.example.dailyconsumption.injection.module.NetworkModule
import com.example.dailyconsumption.login.LoginPresenter
import com.example.dailyconsumption.userlanding.UserLandingPresenter
import com.example.dailyconsumption.userlanding.landingfragments.wifiactivity.WifiConnectionPresenter

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ContextModule::class),(NetworkModule::class)])
interface PresenterInjector {

    fun injectLogin(loginPresenter: LoginPresenter)
    fun injectUserLanding(userLandingPresenter: UserLandingPresenter)
    fun injectWifiConnection(wifiConnectionPresenter: WifiConnectionPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector
        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}