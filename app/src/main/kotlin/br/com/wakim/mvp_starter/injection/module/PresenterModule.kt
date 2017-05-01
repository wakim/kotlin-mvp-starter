package br.com.wakim.mvp_starter.injection.module

import br.com.wakim.mvp_starter.data.remote.PostDataSource
import br.com.wakim.mvp_starter.injection.ConfigPersistent
import br.com.wakim.mvp_starter.ui.DefaultScheduler
import br.com.wakim.mvp_starter.ui.posts_list.PostListContract
import br.com.wakim.mvp_starter.ui.posts_list.PostListPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @ConfigPersistent
    @Provides
    fun providesPostsListPresenter(dataSource: PostDataSource): PostListContract.Presenter =
            PostListPresenter(dataSource, DefaultScheduler)
}