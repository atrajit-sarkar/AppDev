package com.example.instasexies

import retrofit2.http.GET
import retrofit2.http.Path

interface gGitHubApiService {
    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepoContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String = ""
    ): List<RepoFile>
}
