package edu.washington.info448.hatemail

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

class HateMailApp :Application(){
//    private lateinit var sharedPreferences: SharedPreferences
    private var isNightModeEnabled:Boolean = false

    override fun onCreate() {
        super.onCreate()

        val mPrefs:SharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this)
//        isNightModeEnabled = mPrefs.getBoolean("DARK_MODE", false)
        isNightModeEnabled = mPrefs.getBoolean("darkMode", false)
        Log.i("hahahaha", isNightModeEnabled.toString())

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
