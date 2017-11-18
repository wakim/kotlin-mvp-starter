package br.com.wakim.mvp_starter.ui.posts_list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import br.com.wakim.mvp_starter.R
import br.com.wakim.mvp_starter.data.remote.model.Post
import br.com.wakim.mvp_starter.databinding.ActivityPostsListBinding
import br.com.wakim.mvp_starter.ui.BaseActivity
import javax.inject.Inject

class PostListActivity : BaseActivity(), PostListContract.View {

    @Inject
    lateinit var presenter: PostListContract.Presenter

    private lateinit var binding: ActivityPostsListBinding

    private val adapter: PostsAdapter by lazy(LazyThreadSafetyMode.NONE) { PostsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_posts_list)

        presenter.attachView(this)

        configureUI()

        if (savedInstanceState == null) {
            presenter.getPosts()
        }
    }

    private fun configureUI() {
        setSupportActionBar(binding.appBar?.toolbar)
        binding.rvPosts.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)

        if (!configPersistDelegate.instanceSaved) {
            presenter.unsubscribe()
        }
    }

    override fun showNoConnectivityError() {
        showSnack(binding.coordinatorLayout, R.string.no_connectivity, Snackbar.LENGTH_INDEFINITE, R.string.try_again, {
            presenter.getPosts()
        })
    }

    override fun showUnknownError() {
        showSnack(binding.coordinatorLayout, R.string.unknown_error, Snackbar.LENGTH_INDEFINITE, R.string.try_again, {
            presenter.getPosts()
        })
    }

    override fun setLoadingIndicatorVisible(loadingVisible: Boolean) {
        binding.loading.visibility = if (loadingVisible) View.VISIBLE else View.GONE
    }

    override fun showPosts(posts: List<Post>) {
        adapter.setList(posts)
    }
}
