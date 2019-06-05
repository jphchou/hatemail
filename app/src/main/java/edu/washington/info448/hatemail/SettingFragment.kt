package edu.washington.info448.hatemail

import android.content.Context
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

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey)
    }

}

