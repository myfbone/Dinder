package com.github.dinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.dinder.R
import com.github.dinder.model.Message
import kotlinx.android.synthetic.main.item_bubble.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    private val messages : ArrayList<Message> = arrayListOf()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bubble, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.txtText.text = messages[position].text
        holder.view.txtTime.text = dateFormat(messages[position].time)
    }

    override fun getItemCount() = messages.size

    fun addMsg(text : String, date : Date) {
        messages.add(Message(text, date))
        notifyDataSetChanged()
    }

    private fun dateFormat(date : Date) : String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }
}