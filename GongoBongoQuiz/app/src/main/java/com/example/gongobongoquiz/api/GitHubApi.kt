package com.example.gongobongoquiz.api

import com.example.gongobongoquiz.dataModels.GitHubContent
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String = ""
    ): List<GitHubContent>
}