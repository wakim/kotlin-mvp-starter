package br.com.wakim.mvp_starter.ui.posts_list

import br.com.wakim.mvp_starter.BR
import br.com.wakim.mvp_starter.R
import br.com.wakim.mvp_starter.data.remote.model.Post
import br.com.wakim.mvp_starter.ui.DataBindModel
import com.airbnb.epoxy.EpoxyAdapter

class PostsAdapter : EpoxyAdapter() {
    fun setList(posts: List<Post>) {
        removeAllModels()
        addModels(posts.map { DataBindModel<Post>(R.layout.list_item_post, BR.post, it) })
    }
}