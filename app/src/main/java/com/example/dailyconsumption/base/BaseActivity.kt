package com.example.dailyconsumption.base
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



abstract class BaseActivity<P : BasePresenter<BaseView>> : BaseView,AppCompatActivity() {


    protected lateinit var presenter: P

    var settoolbar_status: Boolean = true
    abstract fun getActivityContext(): Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()

    }

    protected abstract fun instantiatePresenter():P

    override fun getContext(): Context {
        return this
    }

}
