package br.com.wakim.mvp_starter.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.wakim.mvp_starter.injection.ActivityComponent
import br.com.wakim.mvp_starter.injection.module.ActivityModule

open class BaseActivity : AppCompatActivity() {

    val configPersistDelegate = ConfigPersistentDelegate()
    lateinit var activityComponent: ActivityComponent

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        configPersistDelegate.onSaveInstanceState(outState ?: return)
    }

    override fun onDestroy() {
        super.onDestroy()
        configPersistDelegate.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        configPersistDelegate.onCreate(this, savedInstanceState)
        activityComponent = configPersistDelegate.component + ActivityModule(this)

        super.onCreate(savedInstanceState)
    }
}