package edu.washington.info448.hatemail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import edu.washington.jchou8.quizdroid.MessageListFragment

const val MESSAGE_LIST_FRAG = "MESSAGE_LIST_FRAG"
const val SEND_FRAG = "SEND_FRAG"

class MessageActivity : AppCompatActivity(),
    MessageListFragment.OnMessageSelectListener,
    SendFragment.OnMessageSendListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val messageListFrag = MessageListFragment.newInstance(ArrayList(HateMail.instance.dataManager.getData()))
        supportFragmentManager.beginTransaction().run {
            replace(R.id.message_container, messageListFrag, MESSAGE_LIST_FRAG)
            commit()
        }
    }

    override fun onMessageSelect(position: Int) {
        val message = HateMail.instance.dataManager.getData()[position]
        val sendFrag = SendFragment.newInstance(message)
        supportFragmentManager.beginTransaction().run {
            replace(R.id.message_container, sendFrag, SEND_FRAG)
            addToBackStack(null)
            commit()
        }
    }

    override fun onMessageSend(phone: String, freq: Long, url: String, fields: Map<String, String>) {
        var reqUrl = "http://www.foaas.com%s".format(url)
        fields.forEach{
            reqUrl = reqUrl.replace(":%s".format(it.key), it.value)
        }

        val queue = Volley.newRequestQueue(applicationContext)
        val stringRequest = object: StringRequest(Request.Method.GET, reqUrl,
            Response.Listener<String> { response ->
                Log.i("MESSAGEACTIVITY", "Response is: $response")
            },
            Response.ErrorListener {error ->
                Log.e(TAG, "Request failed: %s".format(error.toString()))
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "text/plain"
                return headers
            }
        }
        queue.add(stringRequest)
    }
}
