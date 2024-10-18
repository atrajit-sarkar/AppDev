package com.example.instasexies

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RepoViewModel : ViewModel() {
    var repoFiles by mutableStateOf<List<RepoFile>>(emptyList())
        private set

    fun fetchRepoContents(owner: String, repo: String, path: String = "") {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRepoContents(owner, repo, path)
                repoFiles = response.filter {
                    it.type == "file" &&
                            (it.name.endsWith(".jpg") || it.name.endsWith(".png") || it.name.endsWith(".mp4"))
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
