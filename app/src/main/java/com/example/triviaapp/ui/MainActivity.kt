package com.example.triviaapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.triviaapp.R
import com.example.triviaapp.data.entity.User
import com.example.triviaapp.databinding.ActivityMainBinding
import com.example.triviaapp.util.TimeUtil
import com.example.triviaapp.util.isOk
import com.example.triviaapp.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding
        get () = _binding!!

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setTheme(R.style.Theme_TriviaApp)

        setContentView( binding.root )

        binding.mainBtnStart.setOnClickListener {
            if( binding.mainEtName.text.toString().isOk() )
                startGame( binding.mainEtName.text.toString() )
            else
                Toast.makeText(this , "Insert Valid Name!" , Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startGame(name: String) {
        Intent(this , QuizActivity::class.java).apply {
            putExtra( "USER" , User( TimeUtil.getMSfromEpoch() , name ))
        }.also {
            startActivity(it)
        }
    }
}