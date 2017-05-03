package br.com.wakim.mvp_starter.data

import br.com.wakim.mvp_starter.data.remote.Api
import br.com.wakim.mvp_starter.data.remote.model.Post
import io.reactivex.Observable

interface PostDataSource {
    fun getPosts(): Observable<List<Post>>
}

class PostRepository(private val api: Api) : PostDataSource {
    override fun getPosts(): Observable<List<Post>> = api.getPosts()
}