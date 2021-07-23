package com.example.triviaapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaapp.data.entity.QnA
import com.example.triviaapp.data.entity.User
import com.example.triviaapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    lateinit var user : User
    var questionNumber = 0
    val quizes = mutableListOf<QnA>()

    fun insertQnA( q : QnA ) = viewModelScope.launch(Dispatchers.IO){
        quizes.add(q)
        repository.insertQnA( q )
    }

    fun insertUser() = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser( user )
    }

    fun getAllUsers() = repository.getAllUsers()

    suspend fun getQuestionById( userInstance : Long ) = repository.getQuestionById(userInstance)

    fun getUserWithQnA( userInstance: Long ) = repository.getUserWithQnA(userInstance)
}