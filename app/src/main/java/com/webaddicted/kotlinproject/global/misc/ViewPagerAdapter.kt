package com.webaddicted.kotlinproject.global.misc

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

/**
 * Created by Deepak Sharma(webaddicted) on 11-01-2020.
 */
class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(manager) {
    private val mFragmentList: MutableList<Fragment> =
        ArrayList<Fragment>()
    private val mFragmentTitleList: MutableList<String> =
        ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList[position]
    }

}

