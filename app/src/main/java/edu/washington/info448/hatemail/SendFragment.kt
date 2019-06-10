package edu.washington.info448.hatemail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import edu.washington.jchou8.quizdroid.InsultListFragment
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
                if (field == "From") {
                    val edtResID = resources.getIdentifier("edt_param%d".format(i+1), "id", context!!.packageName)
                    val edtView = rootView.findViewById<TextView>(edtResID)
                    edtView.text = PreferenceManager.getDefaultSharedPreferences(rootView.context).getString("editParameter", "")
                }
            } else {
                val edtResID = resources.getIdentifier("edt_param%d".format(i+1), "id", context!!.packageName)
                val edtView = rootView.findViewById<TextView>(edtResID)
                txtView.visibility = View.INVISIBLE
                edtView.visibility = View.INVISIBLE
            }
        }

        val scheduleBtn = rootView.findViewById<Button>(R.id.btn_schedule)
        scheduleBtn.setOnClickListener {
            val edtPhone = rootView.findViewById<TextView>(R.id.edt_phone)
            val edtFreq = rootView.findViewById<TextView>(R.id.edt_freq)
            val fields = mutableMapOf<String, String>()
            if (edtPhone.text.isBlank()) {
                Toast.makeText(context, "No phone number provided.", Toast.LENGTH_SHORT).show()
            } else if (edtFreq.text.isBlank()) {
                Toast.makeText(context, "No frequency provided.", Toast.LENGTH_SHORT).show()
            } else {
                val freq = edtFreq.text.toString().toIntOrNull()
                if (freq === null || freq <= 0) {
                    Toast.makeText(context, "Invalid frequency provided.", Toast.LENGTH_SHORT).show()
                } else {
                    var allFieldsValid = true
                    for (i in 0..2) {
                        val edtResID = resources.getIdentifier("edt_param%d".format(i + 1), "id", context!!.packageName)
                        val edtView = rootView.findViewById<TextView>(edtResID)
                        if (message!!.fields.size >= i + 1) {
                            if (edtView.text.isBlank()) {
                                val field = message!!.fields[i]
                                Toast.makeText(context, "No value provided for %s.".format(field), Toast.LENGTH_SHORT)
                                    .show()
                                allFieldsValid = false
                                break
                            } else {
                                fields[message!!.fields[i].toLowerCase()] = edtView.text.toString()
                            }
                        }
                    }

                    if (allFieldsValid) {
                        listener!!.onMessageSend(
                            edtPhone.text.toString(),
                            (freq * 1000 * 60).toLong(),
                            message!!.url,
                            fields
                        )
                    }
                }
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
        fun onMessageSend(phone: String, freq: Long, url: String, fields: Map<String, String>)
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
