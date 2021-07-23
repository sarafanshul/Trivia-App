package com.example.triviaapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.data.entity.QnA
import com.example.triviaapp.data.entity.User
import com.example.triviaapp.databinding.LayoutRvHistoryBinding
import com.example.triviaapp.util.TimeUtil
import com.example.triviaapp.util.chop

class RecyclerViewHistoryAdapter : RecyclerView.Adapter<RecyclerViewHistoryAdapter.LayoutViewHolder>() {

    private var users : MutableList<User> = mutableListOf()
    private var qnAs : MutableList<List<QnA>> = mutableListOf()

    inner class LayoutViewHolder( private val binding : LayoutRvHistoryBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(user : User, position : Int , qna : List<QnA>) =  with(binding){
            hTwIdx.text = "Game ${position +  1}"
            hTwDate.text = TimeUtil.fromMStoDateString(user.userInstance)
            hTwName.text = user.name
            hTwQna.text = qna.joinToString("\n") {
                "Question: ${it.question.chop(45)}\nAnswer  : ${it.answer.chop(45)}"
            }
            Log.d("Recycler View H" , position.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutViewHolder {
        val binding = LayoutRvHistoryBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return LayoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LayoutViewHolder, position: Int) {
        holder.bind(users[position] , position , qnAs[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun addData( X : User , Y : List<QnA> ){
        users.add( X )
        qnAs.add( Y )
        notifyItemInserted( itemCount - 1 )
    }
}