package edu.washington.info448.hatemail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import edu.washington.info448.hatemail.*

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val histories = arrayListOf<History>(his1, his2, his3)


    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> HistoryFragment.newInstance(histories)
        1 -> HistoryFragment.newInstance(histories)
        2 -> HistoryFragment.newInstance(histories)
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Tab 1 Item"
        1 -> "Tab 2 Item"
        2 -> "Tab 3 Item"
        else -> ""
    }

    override fun getCount(): Int = 3
}