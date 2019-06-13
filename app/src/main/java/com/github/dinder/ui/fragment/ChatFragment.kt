package com.github.dinder.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dinder.R
import com.github.dinder.adapter.ChatAdapter
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*

class ChatFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_chat

    private var recyclerView: RecyclerView? = null
    private lateinit var viewAdapter: ChatAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onResume() {
        super.onResume()
        viewManager = LinearLayoutManager(activity)
        viewAdapter = ChatAdapter()

        recyclerView = activity?.findViewById(R.id.chat)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = viewManager
        recyclerView?.adapter = viewAdapter

        send.setOnClickListener {
            viewAdapter.addMsg(msg.text.toString(), Date())
            msg.setText("")
        }
    }
}