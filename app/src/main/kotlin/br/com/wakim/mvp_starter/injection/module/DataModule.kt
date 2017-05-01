package br.com.wakim.mvp_starter.injection.module

import br.com.wakim.mvp_starter.data.remote.Api
import br.com.wakim.mvp_starter.data.remote.PostDataSource
import br.com.wakim.mvp_starter.data.remote.PostRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun providesPostDataSource(api: Api): PostDataSource = PostRepository(api)
}