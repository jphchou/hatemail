package edu.washington.info448.hatemail

import android.app.Application
import android.util.Log

class HateMail: Application() {
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
    }
}