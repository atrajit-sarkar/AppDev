package com.example.galleryapp.networkCall

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Headers

data class RepoFile(
    val name: String,
    val path: String,
    val type: String,  // "file" or "dir"
    val download_url: String?
)


interface GitHubApiService {
    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepoContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String = ""
    ): List<RepoFile>
}
