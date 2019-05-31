package edu.washington.info448.hatemail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import edu.washington.jchou8.quizdroid.MessageListFragment
import kotlinx.android.synthetic.main.fragment_send.*

private const val ARG_MESSAGE = "message"

class SendFragment: Fragment() {
    private var message: MessageType? = null
    private var listener: OnMessageSendListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = it.getParcelable(ARG_MESSAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_send, container, false)

        for (i in 0..2) {
            val txtResID = resources.getIdentifier("txt_param%d".format(i+1), "id", context!!.packageName)
            val txtView = rootView.findViewById<TextView>(txtResID)

            if (message!!.fields.size >= i + 1) {
                val field = message!!.fields[i]
                txtView.text = field
            } else {
                val edtResID = resources.getIdentifier("edt_param%d".format(i+1), "id", context!!.packageName)
                val edtView = rootView.findViewById<TextView>(edtResID)
                txtView.visibility = View.INVISIBLE
                edtView.visibility = View.INVISIBLE
            }
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnMessageSendListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnMessageSendListener {
        fun onMessageSend(position: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(message: MessageType) =
            SendFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MESSAGE, message)
                }
            }
    }

}
