package com.test.skb.ui.main

import com.test.skb.Screens
import com.test.skb.bus.RxBusSavedUpdate
import com.test.skb.dagger.ComponentManager
import com.test.skb.interactors.MainInteractor
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<IMainMvpView>() {

   @Inject
   lateinit var router: Router

   @Inject
   lateinit var update: RxBusSavedUpdate

   private val interactor = MainInteractor()

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun isUserSignIn(): Boolean {
      return this.interactor.isUserSignIn()
   }

   fun exit() {
      this.interactor.exit()
      this.router.newRootScreen(Screens.AuthFragmentScreen())
   }

   fun clearSaved() {
      this.interactor.clearSaved()
      this.update.send(Any())
   }
}