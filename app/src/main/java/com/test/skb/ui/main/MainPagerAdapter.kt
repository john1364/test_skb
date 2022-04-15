package com.test.skb.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.skb.ui.main.saved.SavedFragment
import com.test.skb.ui.main.search.SearchFragment

class MainPagerAdapter(fragment: FragmentActivity, private val saved: Boolean) :
   FragmentStateAdapter(fragment) {

   override fun getItemCount(): Int {
      return if (this.saved) 2 else 1
   }

   override fun createFragment(position: Int): Fragment {
      return if (this.saved) {
         when (position) {
            0 -> SearchFragment()
            else -> SavedFragment()
         }
      } else {
         SearchFragment()
      }
   }

}