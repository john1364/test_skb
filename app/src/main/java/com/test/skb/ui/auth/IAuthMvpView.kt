package com.test.skb.ui.auth

import com.test.skb.repositories.server.model.Auth
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IAuthMvpView : MvpView {

   fun setBinding(authData: Auth)

   @StateStrategyType(value = OneExecutionStateStrategy::class)
   fun showError(msg: String)

   fun generateToken()

}