package com.example.instasexies

data class RepoFile(
    val name: String,
    val path: String,
    val type: String,  // "file" or "dir"
    val download_url: String?
)
