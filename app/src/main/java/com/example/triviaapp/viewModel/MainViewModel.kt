package com.example.triviaapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.triviaapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
}