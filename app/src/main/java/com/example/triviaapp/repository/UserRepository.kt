package com.example.triviaapp.repository

import androidx.lifecycle.LiveData
import com.example.triviaapp.data.UserDao
import com.example.triviaapp.data.entity.QnA
import com.example.triviaapp.data.entity.User
import com.example.triviaapp.data.entity.relation.UserWithQnA

class UserRepository( private val dao : UserDao ) {

    fun insertUser( user : User) = dao.insertUser( user )

    fun insertQnA( qnA: QnA) = dao.insertQnA(qnA)

    fun getAllUsers() : LiveData<List<User>> = dao.getAllUsers()

    fun getAllQnA() : LiveData<List<QnA>> = dao.getAllQnA()

    fun getUserWithQnA( userInstance : Long) : LiveData<List<UserWithQnA>> = dao.getUserWithQnA(userInstance)

    fun getQuestionById( userInstance: Long ) : List<QnA> = dao.getQuestionById(userInstance)
}