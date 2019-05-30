package edu.washington.info448.hatemail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val histories = arrayListOf<History>(his1, his2, his3)
    val schedules = arrayListOf<History>(his1, his2, his3)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HistoryFragment.newInstance(histories)
            }
            1 -> HistoryFragment.newInstance(schedules)
            else -> {
                return HistoryFragment.newInstance(histories)
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
