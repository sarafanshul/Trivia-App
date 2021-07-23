package com.example.triviaapp.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.triviaapp.data.entity.QnA
import com.example.triviaapp.data.entity.User

data class UserWithQnA(
    @Embedded val user : User ,
    @Relation (
        parentColumn = "userInstance",
        entityColumn = "userInstance"
    )
    val userAnswer : List<QnA>
)
