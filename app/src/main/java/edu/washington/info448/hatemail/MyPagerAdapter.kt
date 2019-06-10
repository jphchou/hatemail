package edu.washington.info448.hatemail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter


class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
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

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
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
