package edu.washington.info448.hatemail

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import edu.washington.jchou8.quizdroid.InsultListFragment

const val MESSAGE_LIST_FRAG = "MESSAGE_LIST_FRAG"
const val SEND_FRAG = "SEND_FRAG"

class MessageActivity : AppCompatActivity(),
    InsultListFragment.OnMessageSelectListener,
    SendFragment.OnMessageSendListener,
    PreviewDialog.PreviewDialogListener {

    private var message: String? = null
    private var freq: Long? = null
    private var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.SEND_SMS),
                1)
        }
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                2)
        }

        val messageListFrag = InsultListFragment.newInstance(ArrayList(HateMail.instance.dataManager.getData()))
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

    fun getReqUrl(url: String, fields: Map<String, String>): String {
        var reqUrl = "http://www.foaas.com%s".format(url)
        fields.forEach{
            reqUrl = reqUrl.replace(":%s".format(it.key), it.value)
        }
        return reqUrl
    }

    override fun onMessageSend(phone: String, freq: Long, url: String, fields: Map<String, String>) {
        val queue = Volley.newRequestQueue(applicationContext)
        val stringRequest = object: StringRequest(Request.Method.GET, getReqUrl(url, fields),
            Response.Listener<String> { response ->
                val previewDialog = PreviewDialog.newInstance(response)
                previewDialog.show(supportFragmentManager, "preview")
                this.phone = phone
                this.freq = freq
                this.message = response
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

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        Log.i("MESSAGE_ACTIVITY", "scheduling \"%s\" sent to %s every %s ms".format(message, phone, freq))
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, MessageReceiver::class.java)
        intent.putExtra("message", message!!)
        intent.putExtra("phone", phone!!)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val schedule = Schedule(phone!!, message!!, freq!!, pendingIntent)
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            freq!!,
            pendingIntent)
    }
}
