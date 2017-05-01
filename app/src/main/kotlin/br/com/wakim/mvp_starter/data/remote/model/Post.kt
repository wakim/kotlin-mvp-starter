package br.com.wakim.mvp_starter.data.remote.model

data class Post(val id: Long,
                val userId: Long,
                val title: String,
                val body: String)