package br.com.wakim.mvp_starter.ui.posts_list

import br.com.wakim.mvp_starter.data.remote.PostDataSource
import br.com.wakim.mvp_starter.ui.SchedulerContract

class PostListPresenter(private val dataSource: PostDataSource,
                        private val scheduler: SchedulerContract) : PostListContract.Presenter {

    var view: PostListContract.View? = null

    override fun detachView(view: PostListContract.View) {
        this.view = null
    }

    override fun attachView(view: PostListContract.View) {
        this.view = view
    }

    // TODO
}