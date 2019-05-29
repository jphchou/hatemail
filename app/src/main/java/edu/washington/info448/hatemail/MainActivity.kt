package edu.washington.info448.hatemail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val histories = arrayListOf<History>(his1, his2, his3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)

//        val historyFragment = HistoryFragment.newInstance(histories)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, historyFragment, "QUIZ_INTRO_FRAGMENT")
//            .commit()
    }
}
