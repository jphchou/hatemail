package edu.washington.jchou8.quizdroid

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import edu.washington.info448.hatemail.MessageListRecyclerAdapter
import edu.washington.info448.hatemail.MessageType
import edu.washington.info448.hatemail.R

private const val ARG_MESSAGE_TYPES = "messageTypes"

class MessageListFragment: Fragment() {
    private var messageTypes: List<MessageType>? = null
    private var listener: OnMessageSelectListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            messageTypes = it.getParcelableArrayList(ARG_MESSAGE_TYPES)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_message_list, container, false)
        val recyclerView = rootView.findViewById(R.id.list_messages) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(rootView.context, LinearLayout.VERTICAL, false)
        (recyclerView.layoutManager as LinearLayoutManager).isMeasurementCacheEnabled = false
        val adapter = MessageListRecyclerAdapter(messageTypes!!)
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnMessageSelectListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnMessageSelectListener {
        fun onMessageSelect()
    }

    companion object {
        @JvmStatic
        fun newInstance(list: ArrayList<MessageType>) =
            MessageListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_MESSAGE_TYPES, list)
                }
            }
    }
}
