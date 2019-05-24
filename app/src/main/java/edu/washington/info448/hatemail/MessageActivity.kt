package edu.washington.info448.hatemail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import edu.washington.jchou8.quizdroid.MessageListFragment

const val MESSAGE_LIST_FRAG = "MESSAGE_LIST_FRAG"

class MessageActivity : AppCompatActivity(),
    MessageListFragment.OnMessageSelectListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val dummy = arrayListOf(
            MessageType("1341234", "test", listOf("bye")),
            MessageType("2", "test", listOf("bye")),
            MessageType("3", "test", listOf("bye")),
            MessageType("4", "test", listOf("bye")),
            MessageType("5", "test", listOf("bye")),
            MessageType("6", "test", listOf("bye")),
            MessageType("712341", "test", listOf("bye"))
        )

        val messageListFrag = MessageListFragment.newInstance(dummy)
        supportFragmentManager.beginTransaction().run {
            replace(R.id.message_container, messageListFrag, MESSAGE_LIST_FRAG)
            commit()
        }
    }

    override fun onMessageSelect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
