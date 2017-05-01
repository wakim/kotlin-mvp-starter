package br.com.wakim.mvp_starter.ui.posts_list

import android.os.Bundle
import br.com.wakim.mvp_starter.ui.BaseActivity
import javax.inject.Inject

class PostListActivity : BaseActivity(), PostListContract.View {

    @Inject
    lateinit var presenter: PostListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)

        presenter.attachView(this)

        // TODO
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
    }
}