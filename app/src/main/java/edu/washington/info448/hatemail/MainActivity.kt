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
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    var histories = arrayListOf(his1, his2, his3)
    var schedules = arrayListOf(sche1, sche2, sche3)
    lateinit var myHistories: ArrayList<History>
    lateinit var mySchedules: ArrayList<Schedule>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up share preference
        val appSharedPrefs = this.getSharedPreferences("prefs", 0)
        appSharedPrefs.registerOnSharedPreferenceChangeListener(this)
        val prefsEditor = appSharedPrefs.edit()
        val gson = Gson()
        /////
        /*val json = appSharedPrefs.getString("Histories", "")
        val type = object : TypeToken<List<History>>() {
        }.type*/

        val json2: String = appSharedPrefs.getString("Schedules", "")
        val type2 = object : TypeToken<List<Schedule>>() {
        }.type

        //val histories: ArrayList<History>? = gson.fromJson(json, type)
        val schedules: ArrayList<Schedule>? = gson.fromJson(json2, type2)
        //this.myHistories = histories ?: arrayListOf()
        this.mySchedules = schedules ?: arrayListOf()

        this.myHistories = HateMail.instance.getHistory()
        this.myHistories.sortByDescending{it.time}

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        fragmentAdapter.myHistories = this.myHistories
        fragmentAdapter.mySchedules = this.mySchedules
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflatar = menuInflater
        inflatar.inflate(R.menu.cos_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        val settingFragment = SettingFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, settingFragment, "SETTING_FRAGMENT")
//            .commit()
        val nextActivity = when(item!!.itemId) {
            R.id.schedule -> MessageActivity::class.java
            else -> PreferencesActivity::class.java
        }
        val intent = Intent(this, nextActivity)

        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if(key == "Histories") {
            val json = applicationContext!!.getSharedPreferences("prefs", 0).getString("Histories", "")
            val type = object : TypeToken<List<History>>() {
            }.type
            val gson = Gson()
            val histories: ArrayList<History>? = gson.fromJson(json, type)
            val historyList = histories ?: arrayListOf()
            HateMail.instance.updateHistory(historyList)

            finish()
            startActivity(this.intent)
        }
    }
}
