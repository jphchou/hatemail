package edu.washington.info448.hatemail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onMessageSend(position: Int) {

    }
}
