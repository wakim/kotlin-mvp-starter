package br.com.wakim.mvp_starter.ui.posts_list

import br.com.wakim.mvp_starter.ui.BasePresenter
import br.com.wakim.mvp_starter.ui.BaseView

interface PostListContract {
    interface View : BaseView
    interface Presenter : BasePresenter<View>
}