package edu.washington.info448.hatemail

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

class HateMail: Application() {
    private var isNightModeEnabled:Boolean = false
    private var inputParameter:String = ""
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
    }

    fun setParameter(inputParameter: String){
        this.inputParameter = inputParameter
    }

    fun isNightModeEnabled(): Boolean {
        return isNightModeEnabled
    }

    fun setIsNightModeEnabled(isNightModeEnabled: Boolean) {
        this.isNightModeEnabled = isNightModeEnabled
    }
}