package edu.washington.info448.hatemail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.history_list_item.view.*


class ScheduleRecyclerViewAdapter(var schedules:ArrayList<Schedule>) : RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleListViewHolder>(){

    var onScheduleListClickedListenr: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewHolderType: Int): ScheduleListViewHolder =
        ScheduleListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false))


    override fun getItemCount(): Int = schedules.size

    override fun onBindViewHolder(viewHolder: ScheduleListViewHolder, position: Int) {
        viewHolder.bindView(schedules[position].recipient, schedules[position].messsage, schedules[position].frequency.toString(), position)
    }

    inner class ScheduleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(recipient: String, message:String, time: String, position: Int) {
            itemView.recipient.text = recipient
            itemView.message.text = message
            itemView.time.text = "every ${time.toInt() / 1000} sec"
            itemView.setOnClickListener { onScheduleListClickedListenr?.invoke(position) }
        }
    }

}