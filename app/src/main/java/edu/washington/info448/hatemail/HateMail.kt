package edu.washington.info448.hatemail

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HateMail: Application() {
    private var isNightModeEnabled:Boolean = false
    private var inputParameter:String = ""
    private var histories:ArrayList<History> = arrayListOf()
    var shouldRefresh = false
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
        inputParameter = mPrefs.getString("editParameter", "")

        val json = this.getSharedPreferences("prefs", 0).getString("Histories", "")
        val type = object : TypeToken<List<History>>() {
        }.type
        val gson = Gson()
        val histories: ArrayList<History>? = gson.fromJson(json, type)
        val historyList = histories ?: arrayListOf()
        this.histories = historyList
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

    fun setIsNightModeEnabled(isNightModeEnabled: Boolean) {
        this.isNightModeEnabled = isNightModeEnabled
    }

    fun updateHistory(newList: ArrayList<History>) {
        this.histories = newList
    }
}