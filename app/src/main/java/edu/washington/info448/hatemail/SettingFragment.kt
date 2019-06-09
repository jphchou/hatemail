package edu.washington.info448.hatemail

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceScreen
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.prefs.Preferences
import android.preference.Preference as Preference
import android.preference.PreferenceManager
import android.util.Log


class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey)
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(HateMail.instance).registerOnSharedPreferenceChangeListener(this)

    }

    override fun onPause() {
        PreferenceManager.getDefaultSharedPreferences(HateMail.instance).unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        if(key == "darkMode"){
            HateMailApp.getSingletonInstance().setIsNightModeEnabled(PreferenceManager.getDefaultSharedPreferences(HateMail.instance).getBoolean("darkMode", false))
        }

        if(key == "editParameter"){
            HateMailApp.getSingletonInstance().setParameter(PreferenceManager.getDefaultSharedPreferences(HateMail.instance).getString("editParameter", ""))
        }
    }

}

