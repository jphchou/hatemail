package edu.washington.info448.hatemail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val histories = arrayListOf<History>(his1, his2, his3)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FirstFragment()
            }
            1 -> HistoryFragment.newInstance(histories)
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
