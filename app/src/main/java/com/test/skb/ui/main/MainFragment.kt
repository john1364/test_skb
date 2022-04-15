package com.test.skb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.skb.R
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class MainFragment : MvpAppCompatFragment(), IMainMvpView {

   companion object {
      const val SEARCH_DELAY = 750L
   }

   @InjectPresenter
   lateinit var presenter: MainPresenter
   private var binding: ViewDataBinding? = null

   private var tabLayout: TabLayout? = null
   private var viewPager2: ViewPager2? = null
   private var adapter: MainPagerAdapter? = null

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
      return this.binding!!.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      toolbar()
      tab()
   }

   private fun toolbar() {
      val toolbar: Toolbar? = this.binding?.root?.findViewById(R.id.toolbar)
      toolbar?.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
         when (it.itemId){
            R.id.clearSaved -> {
               this.presenter.clearSaved()
            }
            R.id.exit -> {
               this.presenter.exit()
            }
         }
         return@OnMenuItemClickListener false
      })
   }

   private fun tab() {
      this.viewPager2 = this.binding?.root?.findViewById(R.id.viewPager2)
      this.viewPager2?.apply {
         offscreenPageLimit = 2
         isUserInputEnabled = true
      }

      val userSignIn = this.presenter.isUserSignIn()

      this.adapter = MainPagerAdapter(requireActivity(), userSignIn)
      this.viewPager2?.adapter = this.adapter

      this.tabLayout = this.binding?.root?.findViewById(R.id.tabLayout)
      if (!userSignIn) {
         this.tabLayout?.removeTabAt(1)
         this.tabLayout?.visibility = View.GONE
      } else {
         TabLayoutMediator(
            tabLayout!!, viewPager2!!
         ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
               0 -> {
                  tab.text = getString(R.string.tab_search)
               }
               else -> {
                  tab.text = getString(R.string.tab_saved)
               }
            }
         }.attach()
      }
   }

}