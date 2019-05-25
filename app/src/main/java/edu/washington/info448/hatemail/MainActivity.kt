package edu.washington.info448.hatemail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_history.*

class MainActivity : AppCompatActivity() {
    val histories = arrayListOf<History>(his1, his2, his3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val quiz_intro = HistoryFragment.newInstance(histories)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, quiz_intro, "QUIZ_INTRO_FRAGMENT")
            .commit()
    }
}
