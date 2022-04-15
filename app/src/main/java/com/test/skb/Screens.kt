package com.test.skb

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.test.skb.ui.auth.AuthFragment
import com.test.skb.ui.details.DetailsFragment
import com.test.skb.ui.main.MainFragment

import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : SupportAppScreen() {

   class AuthFragmentScreen : SupportAppScreen() {
      override fun getFragment(): Fragment {
         return AuthFragment()
      }

      init {
         screenKey = javaClass.simpleName
      }
   }

   class MainFragmentScreen : SupportAppScreen() {

      override fun getFragment(): Fragment {
         return MainFragment()
      }

      init {
         screenKey = javaClass.simpleName
      }
   }

   class DetailsFragmentScreen(private val pos: Int, private val saved: Boolean) :
      SupportAppScreen() {

      override fun getFragment(): Fragment {
         val fragment = DetailsFragment()
            .apply {
               val bundle = Bundle()
               bundle.putInt("pos", pos)
               bundle.putBoolean("saved", saved)
               arguments = bundle
            }

         return fragment
      }

      init {
         screenKey = javaClass.simpleName
      }
   }

}