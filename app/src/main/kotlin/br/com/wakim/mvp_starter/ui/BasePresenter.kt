package br.com.wakim.mvp_starter.ui

interface BasePresenter<in V : BaseView> {
    fun detachView(view: V)
    fun attachView(view: V)
}