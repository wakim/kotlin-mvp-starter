package br.com.wakim.mvp_starter.ui.posts_list

import br.com.wakim.mvp_starter.data.remote.model.Post
import br.com.wakim.mvp_starter.ui.BasePresenter
import br.com.wakim.mvp_starter.ui.BaseView

interface PostListContract {
    interface View : BaseView {
        fun setLoadingIndicatorVisible(loadingVisible: Boolean)

        fun showPosts(posts: List<Post>)

        fun showNoConnectivityError()
        fun showUnknownError()
    }

    interface Presenter : BasePresenter<View> {
        fun getPosts()
    }
}