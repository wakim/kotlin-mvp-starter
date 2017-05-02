package br.com.wakim.mvp_starter.ui.posts_list

import br.com.wakim.mvp_starter.data.remote.PostDataSource
import br.com.wakim.mvp_starter.data.remote.model.Post
import br.com.wakim.mvp_starter.exception.NoNetworkException
import br.com.wakim.mvp_starter.extensions.addToCompositeDisposable
import br.com.wakim.mvp_starter.ui.SchedulerContract
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class PostListPresenter(private val dataSource: PostDataSource,
                        private val scheduler: SchedulerContract) : PostListContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private val connectableDisposable = CompositeDisposable()

    var view: PostListContract.View? = null

    var posts: List<Post>? = null
    var postsObservable: Observable<List<Post>>? = null

    override fun detachView(view: PostListContract.View) {
        this.view = null
        compositeDisposable.clear()
    }

    override fun attachView(view: PostListContract.View) {
        this.view = view

        posts?.let(this::showPosts)
        postsObservable?.let(this::subscribeToPosts)
    }

    override fun unsubscribe() {
        connectableDisposable.clear()
    }

    override fun getPosts() {
        dataSource.getPosts()
                .subscribeOn(scheduler.io)
                .observeOn(scheduler.ui)
                .replay()
                .apply {
                    subscribeToPosts(this)
                    connectableDisposable.add(connect())
                }
    }

    fun subscribeToPosts(observable: Observable<List<Post>>) {
        postsObservable = observable

        view?.let {
            it.setLoadingIndicatorVisible(true)
            observable
                    .doOnError { _ ->
                        it.setLoadingIndicatorVisible(false)
                        postsObservable = null
                    }
                    .doOnComplete {
                        it.setLoadingIndicatorVisible(false)
                        postsObservable = null
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

    fun showPosts(posts: List<Post>) {
        this.posts = posts
        view?.showPosts(posts)
    }
}