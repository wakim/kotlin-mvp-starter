package br.com.wakim.mvp_starter.ui.posts_list

import br.com.wakim.mvp_starter.data.PostDataSource
import br.com.wakim.mvp_starter.data.remote.model.Post
import br.com.wakim.mvp_starter.exception.NoNetworkException
import br.com.wakim.mvp_starter.extensions.addToCompositeDisposable
import br.com.wakim.mvp_starter.ui.SchedulerContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class PostListPresenter(private val dataSource: PostDataSource,
                        private val scheduler: SchedulerContract) : PostListContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    var view: PostListContract.View? = null

    private var posts: List<Post>? = null

    override fun detachView(view: PostListContract.View) {
        this.view = null
    }

    override fun attachView(view: PostListContract.View) {
        this.view = view

        posts?.let(this::showPosts)
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

    override fun getPosts() {
        view?.let {
            it.setLoadingIndicatorVisible(true)

            dataSource.getPosts()
                    .subscribeOn(scheduler.io)
                    .observeOn(scheduler.ui)
                    .doOnError { _ ->
                        it.setLoadingIndicatorVisible(false)
                    }
                    .doOnComplete {
                        it.setLoadingIndicatorVisible(false)
                    }
                    .subscribe (
                            { list ->
                                posts = list
                                showPosts(list)
                            },
                            { e ->
                                when (e) {
                                    is NoNetworkException -> view?.showNoConnectivityError()
                                    else -> {
                                        Timber.e(e)
                                        view?.showUnknownError()
                                    }
                                }
                            }
                    )
                    .addToCompositeDisposable(compositeDisposable)
        }
    }

    private fun showPosts(posts: List<Post>) {
        this.posts = posts
        view?.showPosts(posts)
    }
}