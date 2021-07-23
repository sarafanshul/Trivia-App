package com.example.triviaapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity( primaryKeys = ["userInstance" , "question"] )
data class QnA(
    val userInstance : Long ,
    val question : String ,
    val answer : String
):Serializable
