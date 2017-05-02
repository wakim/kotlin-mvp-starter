package br.com.wakim.mvp_starter.ui.posts_list

import android.os.Bundle
import br.com.wakim.mvp_starter.data.remote.model.Post
import br.com.wakim.mvp_starter.ui.BaseActivity
import javax.inject.Inject

class PostListActivity : BaseActivity(), PostListContract.View {

    @Inject
    lateinit var presenter: PostListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)

        presenter.attachView(this)

        if (savedInstanceState == null) {
            presenter.getPosts()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)

        if (!configPersistDelegate.instanceSaved) {
            presenter.unsubscribe()
        }
    }

    override fun showNoConnectivityError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUnknownError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoadingIndicatorVisible(loadingVisible: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPosts(posts: List<Post>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}