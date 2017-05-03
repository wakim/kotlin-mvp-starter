package br.com.wakim.mvp_starter.tests

import br.com.wakim.mvp_starter.data.PostDataSource
import br.com.wakim.mvp_starter.data.remote.model.Post
import br.com.wakim.mvp_starter.exception.NoNetworkException
import br.com.wakim.mvp_starter.ui.ImmediateScheduler
import br.com.wakim.mvp_starter.ui.posts_list.PostListContract
import br.com.wakim.mvp_starter.ui.posts_list.PostListPresenter
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

@Suppress("IllegalIdentifier")
class PostListPresenterTest {

    lateinit var presenter: PostListPresenter
    lateinit var view: PostListContract.View
    lateinit var dataSource: PostDataSource

    @Before
    fun setup() {
        view = mock()
        dataSource = mock()

        presenter = PostListPresenter(dataSource, ImmediateScheduler)
        presenter.attachView(view)
    }

    @After
    fun dispose() {
        presenter.detachView(view)
    }

    @Test
    fun `should alert no connectivity to view when fetching posts`() {
        `when`(dataSource.getPosts())
                .thenReturn(Observable.error(NoNetworkException))

        presenter.getPosts()

        verify(view).setLoadingIndicatorVisible(true)
        verify(view).setLoadingIndicatorVisible(false)
        verify(view).showNoConnectivityError()

        verify(dataSource).getPosts()
    }

    @Test
    fun `should alert unknown error to view when fetching posts`() {
        `when`(dataSource.getPosts())
                .thenReturn(Observable.error(NullPointerException()))

        presenter.getPosts()

        verify(view).setLoadingIndicatorVisible(true)
        verify(view).setLoadingIndicatorVisible(false)
        verify(view).showUnknownError()

        verify(dataSource).getPosts()
    }

    @Test
    fun `should show posts list when fetching posts`() {
        val posts = listOf(Post(1, 1, "title 1", "body 1"), Post(2, 1, "title 2", "body 2"), Post(3, 1, "title 3", "body 3"))

        `when`(dataSource.getPosts())
                .thenReturn(Observable.just(posts))

        presenter.getPosts()

        verify(view).setLoadingIndicatorVisible(true)
        verify(view).setLoadingIndicatorVisible(false)
        verify(view).showPosts(posts)

        verify(dataSource).getPosts()
    }

    @Test
    fun `should show posts list to new view when fetching posts`() {
        val posts = listOf(Post(1, 1, "title 1", "body 1"), Post(2, 1, "title 2", "body 2"), Post(3, 1, "title 3", "body 3"))

        `when`(dataSource.getPosts())
                .thenReturn(Observable.just(posts))

        presenter.detachView(view)

        presenter.getPosts()

        presenter.attachView(view)

        verify(view).showPosts(posts)

        verify(view).setLoadingIndicatorVisible(true)
        verify(view).setLoadingIndicatorVisible(false)

        verify(dataSource).getPosts()
    }
}