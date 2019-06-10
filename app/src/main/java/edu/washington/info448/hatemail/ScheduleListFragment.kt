package edu.washington.info448.hatemail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_history.*
import android.widget.TextView

import kotlinx.android.synthetic.main.fragment_history.*


class ScheduleListFragment : Fragment() {
    private var listener: OnScheduleSelectListener? = null

    companion object {
        val  SCHEDULE_KEY= "schedule_key"

        fun newInstance(histories: ArrayList<Schedule>): ScheduleListFragment {

            val args = Bundle().apply {
                putParcelableArrayList(SCHEDULE_KEY, histories)
            }

            val fragment = ScheduleListFragment().apply {
                arguments = args
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        Log.i("test", "running fragment")
        arguments?.let {
            val scheduleList = it.getParcelableArrayList<Schedule>(SCHEDULE_KEY)as ArrayList<Schedule>
            val adapter = ScheduleRecyclerViewAdapter(scheduleList)
            val scheduleListView = rootView.findViewById(R.id.historyListView) as RecyclerView
            adapter.onScheduleListClickedListener = { id, position ->
                listener!!.onScheduleSelect(id, position)
            }
            scheduleListView.adapter = adapter
            scheduleListView.setHasFixedSize(true)
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnScheduleSelectListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnScheduleSelectListener {
        fun onScheduleSelect(id: Int, position: Int)
    }

}
