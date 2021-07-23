package com.example.triviaapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.triviaapp.data.entity.QnA
import com.example.triviaapp.data.entity.User
import com.example.triviaapp.data.entity.relation.UserWithQnA

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser( user : User )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQnA( qnA: QnA )

    @Query("SELECT * FROM user")
    fun getAllUsers() : LiveData<List<User>>

    @Query("SELECT * FROM qna")
    fun getAllQnA() : LiveData<List<QnA>>

    @Transaction
    @Query("SELEcT * FROM user WHERE userInstance = :userInstance")
    fun getUserWithQnA( userInstance : Long) : LiveData<List<UserWithQnA>>

    @Query("SELECT * FROM qna WHERE userInstance = :userInstance")
    fun getQuestionById( userInstance: Long ) : List<QnA>

}