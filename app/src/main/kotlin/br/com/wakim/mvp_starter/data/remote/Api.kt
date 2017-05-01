package br.com.wakim.mvp_starter.data.remote

import br.com.wakim.mvp_starter.data.remote.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>
}