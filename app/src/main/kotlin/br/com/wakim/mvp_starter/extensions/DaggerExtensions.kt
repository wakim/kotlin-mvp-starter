package br.com.wakim.mvp_starter.extensions

import android.content.Context
import br.com.wakim.mvp_starter.AppApplication

val Context.appComponent
    get() = (applicationContext as AppApplication).appComponent