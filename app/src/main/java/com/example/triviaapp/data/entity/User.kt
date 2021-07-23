package com.example.triviaapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(

    @PrimaryKey(autoGenerate = false)
    val userInstance : Long ,
    val name : String ,
):Serializable
