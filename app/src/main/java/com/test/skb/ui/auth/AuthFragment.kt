package com.test.skb.ui.auth

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.test.skb.R
import com.test.skb.repositories.server.model.Auth
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class AuthFragment : MvpAppCompatFragment(), IAuthMvpView {

   @InjectPresenter
   lateinit var presenter: AuthPresenter
   private var binding: ViewDataBinding? = null

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
      return this.binding!!.root
   }

   override fun setBinding(authData: Auth) {
      this.binding!!.setVariable(BR.presenter, this.presenter)
      this.binding!!.setVariable(BR.auth, authData)
   }

   override fun showError(msg: String) {
      AlertDialog.Builder(requireActivity())
         .setTitle("Ошибка")
         .setMessage(msg)
         .setPositiveButton(android.R.string.ok, null)
         .show()
   }

   override fun generateToken() {
      CustomTabsIntent.Builder()
         .build()
         .launchUrl(
            requireActivity(),
            Uri.parse("https://github.com/settings/tokens/new?description=test%20app%20token")
         )
   }
}