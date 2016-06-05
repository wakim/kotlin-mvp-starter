package com.app.id.activity

import android.os.Bundle
import com.app.id.R
import com.app.id.controller.ApiController
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}
