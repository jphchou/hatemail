package edu.washington.info448.hatemail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log

class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message")
        val phone = intent.getStringExtra("phone")

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            phone,
            null,
            message,
            null,
            null
        )

        Log.i("MESSAGE_RECEIVER", "sending %s to %s".format(message, phone))
    }
}
