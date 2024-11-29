package com.example.gongobongoquiz.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gongobongoquiz.api.ApiService
import com.example.gongobongoquiz.dataModels.GitHubContent
import com.example.gongobongoquiz.dataModels.QuizQuestion
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URL

class MainViewModel : ViewModel() {
    // Mutable state for folder contents
    private val _contents = MutableStateFlow<List<GitHubContent>>(emptyList())
    val contents: StateFlow<List<GitHubContent>> = _contents

    // Mutable state for quiz questions
    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions

    // Fetch folder contents
    fun fetchContents(owner: String, repo: String, path: String = "") {
        viewModelScope.launch {
            try {
                val result = ApiService.api.getContents(owner, repo, path)
                _contents.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Load quiz questions from a JSON file
    fun loadQuiz(fileUrl: String) {
        viewModelScope.launch {
            try {
                val json = URL(fileUrl).readText() // Fetch JSON from raw URL
                _quizQuestions.value =
                    Gson().fromJson(json, Array<QuizQuestion>::class.java).toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
