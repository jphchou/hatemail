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
    lateinit var mySchedules: ArrayList<Schedule>

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HistoryFragment.newInstance(myHistories)
            }
            1 -> ScheduleListFragment.newInstance(mySchedules)
            else -> {
                return HistoryFragment.newInstance(myHistories)
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "History"
            1 -> "Schedule"
            else -> {
                return "History"
            }
        }
    }
}
