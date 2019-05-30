package edu.washington.info448.hatemail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import android.preference.PreferenceManager
import android.content.SharedPreferences





class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var histories = arrayListOf(his1, his2, his3)
    lateinit var myHistories: ArrayList<History>
//    lateinit var myScedules: ArrayList<Schedule>

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FirstFragment()
            }
            1 -> HistoryFragment.newInstance(myHistories)
            else -> {
                return HistoryFragment.newInstance(histories)
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "First Tab"
            1 -> "Second Tab"
            else -> {
                return "Third Tab"
            }
        }
    }
}
