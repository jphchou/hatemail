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
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import android.preference.PreferenceManager
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener,
    ScheduleListFragment.OnScheduleSelectListener,
    ConfirmDialog.ConfirmDialogListener {

    var curScheduleId: Int? = null
    var curSchedulePosition: Int? = null
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

        val json2: String = appSharedPrefs.getString("Schedules", "")
        val type2 = object : TypeToken<List<Schedule>>() {
        }.type

        //val histories: ArrayList<History>? = gson.fromJson(json, type)
        val schedules: ArrayList<Schedule>? = gson.fromJson(json2, type2)
        //this.myHistories = histories ?: arrayListOf()
        this.mySchedules = schedules ?: arrayListOf()

        this.myHistories = HateMail.instance.getHistory()
        this.myHistories.sortByDescending{it.time}
        this.mySchedules = HateMail.instance.getSchedules()

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
            historyList.sortByDescending{it.time}

            (viewpager_main.adapter!! as MyPagerAdapter).myHistories = historyList
            (viewpager_main.adapter!! as MyPagerAdapter).notifyDataSetChanged()
        }

        if(key == "Schedules") {
            val json = applicationContext!!.getSharedPreferences("prefs", 0).getString("Schedules", "")
            val type = object : TypeToken<List<Schedule>>() {
            }.type
            val gson = Gson()
            val schedules: ArrayList<Schedule>? = gson.fromJson(json, type)
            val scheduleList = schedules ?: arrayListOf()
            HateMail.instance.updateSchedules(scheduleList)

            (viewpager_main.adapter!! as MyPagerAdapter).mySchedules = scheduleList
            viewpager_main.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onScheduleSelect(id: Int, position: Int) {
        curScheduleId = id
        curSchedulePosition = position
        val confirmDialog = ConfirmDialog.newInstance()
        confirmDialog.show(supportFragmentManager, "confirm")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, MessageReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, curScheduleId!!, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
        curScheduleId = null

        val appSharedPrefs = this.getSharedPreferences("prefs", 0)
        val prefsEditor = appSharedPrefs.edit()
        var curJson = appSharedPrefs.getString("Schedules", "")
        val gson = Gson()
        val type = object : TypeToken<List<Schedule>>() {
        }.type
        var curSchedules: MutableList<Schedule>? = gson.fromJson(curJson, type)
        if (curSchedules == null) {
            curSchedules = mutableListOf()
        } else {
            curSchedules.removeAt(curSchedulePosition!!)
        }
        prefsEditor.putString("Schedules", gson.toJson(curSchedules))
        prefsEditor.commit()
    }
}
