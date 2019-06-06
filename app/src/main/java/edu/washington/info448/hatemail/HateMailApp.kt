package edu.washington.info448.hatemail

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

class HateMailApp :Application(){
//    private lateinit var sharedPreferences: SharedPreferences
    private var isNightModeEnabled:Boolean = false
    private var inputParameter:String = ""

    override fun onCreate() {
        super.onCreate()

        val mPrefs:SharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this)
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

    companion object {
        const val TAG = "Application"
        private var instance: HateMailApp? = null

        fun getSingletonInstance(): HateMailApp {
            return instance!!
        }
    }

    init {
        instance = this
    }
}
