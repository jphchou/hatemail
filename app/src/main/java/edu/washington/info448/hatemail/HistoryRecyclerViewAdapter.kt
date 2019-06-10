package edu.washington.info448.hatemail

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.history_list_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class HistoryRecyclerViewAdapter(var histories:ArrayList<History>) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder>(){

    var onHistoryClickedListenr: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewHolderType: Int): HistoryViewHolder =
        HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false))


    override fun getItemCount(): Int = histories.size

    override fun onBindViewHolder(viewHolder: HistoryViewHolder, position: Int) {
        viewHolder.bindView(histories[position].recipient, histories[position].message,histories[position].time, position)
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(recipient: String, message:String, time: Date, position: Int) {
            itemView.recipient.text = message
            itemView.message.text = recipient
            itemView.time.text = DateUtils.getRelativeTimeSpanString(time.time)
            itemView.setOnClickListener { onHistoryClickedListenr?.invoke(position) }
        }
    }

}