package br.com.wakim.mvp_starter.tests

import br.com.wakim.mvp_starter.data.remote.PostDataSource
import br.com.wakim.mvp_starter.ui.ImmediateScheduler
import br.com.wakim.mvp_starter.ui.posts_list.PostListContract
import br.com.wakim.mvp_starter.ui.posts_list.PostListPresenter
import com.nhaarman.mockito_kotlin.mock
import org.junit.After
import org.junit.Before

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
}