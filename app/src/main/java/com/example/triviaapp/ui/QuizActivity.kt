package com.example.triviaapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.adapter.RecyclerViewHistoryAdapter
import com.example.triviaapp.constant.Quiz
import com.example.triviaapp.data.entity.QnA
import com.example.triviaapp.data.entity.User
import com.example.triviaapp.databinding.ActivityQuizBinding
import com.example.triviaapp.util.chop
import com.example.triviaapp.viewModel.QuizViewModel
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    private var _binding : ActivityQuizBinding ?= null
    private val binding : ActivityQuizBinding
        get() = _binding!!

    private val viewModel : QuizViewModel by viewModels()

    companion object{
        private const val TAG = "QuizActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityQuizBinding.inflate(layoutInflater)
        viewModel.user = intent.getSerializableExtra("USER") as User

        setContentView(binding.root)

        newQuestion()

        binding.quizBtn.setOnClickListener {
            val qnA = QnA(
                viewModel.user.userInstance ,
                Quiz.questions[viewModel.questionNumber],
                when( Quiz.type[ viewModel.questionNumber ] ){
                    Quiz.SINGLE_CHOICE_QUESTION -> {
                        Quiz.choices[viewModel.questionNumber][radioGroup?.checkedRadioButtonId?.minus(
                            viewModel.questionNumber
                        )!!]
                    }
                    Quiz.MULTIPLE_CHOICE_QUESTION -> {
                        choiceGroup?.filter { it.isChecked }!!.joinToString(",") { it.text }
                    }
                    else -> ""
                }
            )
            viewModel.insertQnA( qnA )
            Log.d(TAG, "onCreate: $qnA")
            viewModel.questionNumber++
            if( viewModel.questionNumber == Quiz.totalQuizes() ) {
                Log.d(TAG, "onCreate: ${viewModel.user}")
                viewModel.insertUser()
                showResults()
            }
            else
                newQuestion()
        }

        binding.quizBtnRestart.setOnClickListener {
            finish()
        }

        binding.quizBtnHistory.setOnClickListener {
            deleteMe()
            showHistory()
        }
    }

    private var radioGroup: RadioGroup? = null
    private var choiceGroup : MutableList<MaterialCheckBox>? = null
    private var rv : RecyclerView ?= null
    private var adapter : RecyclerViewHistoryAdapter ?= null
    private var linearLayout : LinearLayout ?= null
    private var questionView  : MaterialTextView ?= null

    /**
     * Function to show history in a recycler view.
     */
    private fun showHistory() {
        binding.quizBtnHistory.visibility = View.GONE
        rv = RecyclerView(this)
        rv?.layoutManager = LinearLayoutManager(this)
        rv?.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        rv?.addItemDecoration(
            DividerItemDecoration( rv?.context , DividerItemDecoration.VERTICAL )
        )
        adapter = RecyclerViewHistoryAdapter()
        rv?.adapter = adapter
        binding.quizLl.addView(rv)

        viewModel.getAllUsers().observe(this , observeUser@{data ->
            if( data.isNullOrEmpty() ) return@observeUser
            data.forEach {
                viewModel.getUserWithQnA(it.userInstance).observe (this , observeQnA@{ X ->
                    if( X.isNullOrEmpty() ) return@observeQnA
                    adapter?.addData( X.first().user , X.first().userAnswer )
                    Log.d(TAG, "showHistory: ${X.toMutableList().toString()}")
                })
            }
        })
    }

    /**
     * Queries next question and create the view for the question
     */
    private fun newQuestion(){
        deleteMe()
        val question = Quiz.questions[viewModel.questionNumber]
        val choice = Quiz.choices[viewModel.questionNumber]
        val type = Quiz.type[viewModel.questionNumber]
        createQuestion(question, choice, type)
    }

    private fun createQuestion(question: String, choice: List<String>, type: Int) {

        linearLayout = LinearLayout(this)
        linearLayout?.orientation = LinearLayout.VERTICAL

        questionView = MaterialTextView(this)
        questionView?.text = question
        questionView?.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT ,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        questionView?.textSize = 24F

        linearLayout?.addView( questionView )

        when( type ){
            Quiz.SINGLE_CHOICE_QUESTION -> {
                radioGroup = RadioGroup(this)
                radioGroup?.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                for (i in choice.indices) {
                    val radioButton = RadioButton(this)
                    radioButton.id = viewModel.questionNumber + i
                    radioButton.text = choice[i]
                    radioGroup?.addView(radioButton)
                    if( i == 0 )
                        radioButton.isChecked = true
                }
                linearLayout?.addView(radioGroup)
//                choiceView.checkedRadioButtonId
            }
            Quiz.MULTIPLE_CHOICE_QUESTION -> {
                choiceGroup = mutableListOf()
                for ( i in choice.indices) {
                    val checkBox = MaterialCheckBox(this)
                    checkBox.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    checkBox.text = choice[i]
                    checkBox.id = viewModel.questionNumber + i
                    choiceGroup?.add( checkBox )
                }
                for( i in choiceGroup!! )
                    linearLayout?.addView( i )
            }
        }
        binding.quizLl.addView( linearLayout )
    }

    /**
     * Called when all quiz questions are solved and its time to show summary
     */
    private fun showResults() {
        deleteMe()
        binding.quizBtn.visibility = View.GONE
        binding.quizBtnRestart.visibility = View.VISIBLE
        binding.quizBtnHistory.visibility = View.VISIBLE
        showSummary()
        Toast.makeText( this , "Finished!" , Toast.LENGTH_LONG ).show()
    }

    @SuppressLint("SetTextI18n")
    private fun showSummary() {
        questionView = MaterialTextView(this)
        questionView?.apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textSize = 15F
            text = "Summary\n\n${
                viewModel.quizes.joinToString("\n") {
                    "Question: ${it.question.chop(45)}\nAnswer  : ${it.answer.chop(45)}"
                }
            }"
        }
        binding.quizLl.addView(questionView)
    }

    /**
     * All the views and adapters to be collected by GC
     */
    private fun deleteMe(){
        binding.quizLl.removeAllViews()
        radioGroup = null
        choiceGroup = null
        rv = null
        adapter = null
        linearLayout = null
        questionView = null
        viewModel.getAllUsers().removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteMe()
    }
}