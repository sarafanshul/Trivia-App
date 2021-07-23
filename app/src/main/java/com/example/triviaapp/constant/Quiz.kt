package com.example.triviaapp.constant

@Suppress("SpellCheckingInspection")
object Quiz {

    const val MULTIPLE_CHOICE_QUESTION = 1
    const val SINGLE_CHOICE_QUESTION = 0

    val questions : MutableList<String> = mutableListOf(
        "Who is the best cricketer in the world" ,
        "What are the colors in Indian flag ? Select all."
    )

    val choices : MutableList<List<String>> = mutableListOf(
        listOf(
            "Sachin Tendulkar",
            "Virat Kholi",
            "Jack Kallis" ,
            "Adam Gilchrist"
        ) ,
        listOf(
            "White",
            "Yellow",
            "Orange",
            "Green"
        )
    )

    val type : MutableList<Int> = mutableListOf(
        SINGLE_CHOICE_QUESTION ,
        MULTIPLE_CHOICE_QUESTION
    )

    fun totalQuizes() = questions.size

    fun addQuiz( q : String , c : List<String> , t : Int ){
        questions.add(q)
        choices.add(c)
        type.add(t)
    }

}