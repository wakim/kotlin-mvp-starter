package br.com.wakim.mvp_starter.injection

import br.com.wakim.mvp_starter.injection.module.ActivityModule
import br.com.wakim.mvp_starter.ui.posts_list.PostListActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(postListActivity: PostListActivity)
}