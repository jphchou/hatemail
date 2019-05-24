package edu.washington.info448.hatemail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.message_list_item.view.*

class MessageListRecyclerAdapter(var listOfMessages: List<MessageType>): RecyclerView.Adapter<MessageListRecyclerAdapter.MessageViewHolder>() {
    var onMessageClickedListener: ((position: Int, name: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewHolderType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.message_list_item, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfMessages.size
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        // Sets data on view
        viewHolder.bindView(listOfMessages[position].name, position)
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(messageName: String, position: Int) {
            itemView.message_name.text = messageName

            itemView.setOnClickListener {
                onMessageClickedListener?.invoke(position, messageName)
            }
        }
    }
}