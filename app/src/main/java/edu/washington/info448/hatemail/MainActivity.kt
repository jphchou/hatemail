package edu.washington.info448.hatemail

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.io.IOException
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList
import android.R.id.edit




class MainActivity : AppCompatActivity() {
    var histories = arrayListOf<History>(his1, his2, his3)
    lateinit var myHistories: ArrayList<History>
//    lateinit var mySchedules: ArrayList<Schedule>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appSharedPrefs = this.getSharedPreferences("prefs", 0)
        val prefsEditor = appSharedPrefs.edit()
        val gson = Gson()
        /////
        val dummyHistory = gson.toJson(histories)
        prefsEditor.putString("Histories", dummyHistory)
        prefsEditor.commit()
        /////
        val json = appSharedPrefs.getString("Histories", "")
        val type = object : TypeToken<List<History>>() {
        }.type

//        val json2 = appSharedPrefs.getString("Schedules", "")
//        val type2 = object : TypeToken<List<Schedule>>() {
//        }.type

        this.myHistories = gson.fromJson(json, type)
//        this.mySchedules = gson.fromJson(json2, type2)



        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        fragmentAdapter.myHistories = this.myHistories
//        fragmentAdapter.myScedules = this.mySchedules
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)

//        val historyFragment = HistoryFragment.newInstance(histories)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, historyFragment, "QUIZ_INTRO_FRAGMENT")
//            .commit()
    }
}
