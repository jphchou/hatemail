package edu.washington.info448.hatemail

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HateMail: Application() {
    private var isNightModeEnabled:Boolean = false
    private var inputParameter:String = ""
    private var histories:ArrayList<History> = arrayListOf()
    private var schedules:ArrayList<Schedule> = arrayListOf()

    lateinit var dataManager: DataManager
        private set

    companion object {
        lateinit var instance: HateMail
            private set
    }

    override fun onCreate() {

        super.onCreate()
        Log.i("test", "hello")
        instance = this

        val inputStream = assets.open("operations.json")
        dataManager = DataManager(inputStream)
        Log.i("test", dataManager.getData().toString())

        val mPrefs: SharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this)
        isNightModeEnabled = mPrefs.getBoolean("darkMode", false)
        if(isNightModeEnabled == false){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        inputParameter = mPrefs.getString("editParameter", "")

        val json = this.getSharedPreferences("prefs", 0).getString("Histories", "")
        val type = object : TypeToken<List<History>>() {
        }.type
        val gson = Gson()
        val histories: ArrayList<History>? = gson.fromJson(json, type)
        val historyList = histories ?: arrayListOf()
        this.histories = historyList


        val json2 = this.getSharedPreferences("prefs", 0).getString("Schedules", "")
        val type2 = object : TypeToken<List<Schedule>>() {
        }.type
        val schedules: ArrayList<Schedule>? = gson.fromJson(json2, type2)
        val scheduleList = schedules ?: arrayListOf()
        this.schedules = scheduleList
    }

    fun setParameter(inputParameter: String){
        this.inputParameter = inputParameter
    }

    fun isNightModeEnabled(): Boolean {
        return isNightModeEnabled
    }

    fun getHistory(): ArrayList<History> {
        return histories
    }

    fun getSchedules(): ArrayList<Schedule> {
        return schedules
    }

    fun setIsNightModeEnabled(isNightModeEnabled: Boolean) {
        this.isNightModeEnabled = isNightModeEnabled
    }

    fun updateHistory(newList: ArrayList<History>) {
        this.histories = newList
    }

    fun updateSchedules(newList: ArrayList<Schedule>) {
        this.schedules = newList
    }
}