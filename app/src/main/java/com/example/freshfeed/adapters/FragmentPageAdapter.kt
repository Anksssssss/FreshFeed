package com.example.freshfeed.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.freshfeed.fragments.BusinessFragment
import com.example.freshfeed.fragments.EntertainmentFragment
import com.example.freshfeed.fragments.GeneralFragment
import com.example.freshfeed.fragments.HealthFragment
import com.example.freshfeed.fragments.ScienceFragment
import com.example.freshfeed.fragments.SportsFragments
import com.example.freshfeed.fragments.TechnologyFragment

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0->{
               GeneralFragment()
            }
            1->{
                BusinessFragment()
            }
            2->{
                EntertainmentFragment()
            }
            3->{
                HealthFragment()
            }
            4->{
                ScienceFragment()
            }
            5->{
                SportsFragments()
            }
            6->{
                TechnologyFragment()
            }
           else -> {
               GeneralFragment()
           }
       }
    }
}