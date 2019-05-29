package edu.washington.info448.hatemail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_history.*
import android.widget.TextView

import kotlinx.android.synthetic.main.fragment_history.*


class HistoryFragment : Fragment() {
//    private var listener: OnFragmentInteractionListener? = null

    companion object {
        val  HISTORY_KEY= "history_key"

        fun newInstance(histories: ArrayList<History>): HistoryFragment {

            val args = Bundle().apply {
                putParcelableArrayList(HISTORY_KEY, histories)
            }
            val fragment = HistoryFragment().apply {
                arguments = args
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        Log.i("test", "running fragment")
//        arguments?.let {
//            val histories = it.getParcelableArrayList<History>(HISTORY_KEY)as ArrayList<History>
//
//            val adapter = HistoryRecyclerViewAdapter(histories)
//            val historyListView = rootView.findViewById(R.id.historyListView) as RecyclerView
//            historyListView.adapter = adapter
//            historyListView.setHasFixedSize(true)
//        }

        return rootView
    }


}
