package com.example.dailyconsumption.userlanding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class UserLandingFragmentAdapter(fm: FragmentManager, behavior: Int):
    FragmentPagerAdapter(fm,behavior) {
    val fragmentList:MutableList<Fragment> = ArrayList<Fragment>()
    val fragmentTitleList:MutableList<String> = ArrayList<String>()
    val fragmentImageNormal:MutableList<Int> = ArrayList<Int>()
    val fragmentImageBold:MutableList<Int> = ArrayList<Int>()

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title:String, normalImage:Int, boldImage:Int){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
        fragmentImageNormal.add(normalImage)
        fragmentImageBold.add(boldImage)
    }
}