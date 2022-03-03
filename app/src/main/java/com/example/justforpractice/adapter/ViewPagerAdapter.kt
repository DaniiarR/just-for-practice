package com.example.justforpractice.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justforpractice.tasks.list.TaskListFragment
import com.example.justforpractice.utils.Const

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = Const.TAB_PAGES_NUM


    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TaskListFragment()
            1 -> return TaskListFragment()
        }
        return TaskListFragment()
    }
}