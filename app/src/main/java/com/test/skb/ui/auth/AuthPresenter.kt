package com.test.skb.ui.auth

import android.util.Log
import com.test.skb.Screens
import com.test.skb.dagger.ComponentManager
import com.test.skb.interactors.AuthInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AuthPresenter : MvpPresenter<IAuthMvpView>() {

   @Inject
   lateinit var router: Router
   private val interactor = AuthInteractor()

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   override fun onFirstViewAttach() {
      super.onFirstViewAttach()
      viewState.setBinding(this.interactor.getAuth())

      if (this.interactor.isAuth()) {
         signIn()
      }
   }

   fun signIn() {
      this.interactor.authToken()
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe({
            this.router.newRootScreen(Screens.MainFragmentScreen())
         }) {
            if (it.javaClass == HttpException::class.java) {
               it as HttpException
               when (it.code()) {
                  401 -> {
                     viewState.showError("Ошибка авторизации")
                  }
                  else -> {
                     viewState.showError("Попробйту повторить позже\nКод ошибки: ${it.code()}")
                  }
               }

            }
            Log.d("TAG", "signIn: ")
         }
   }

   fun continueWithoutRegistration() {
      this.router.navigateTo(Screens.MainFragmentScreen())
   }

   fun generateToken() {
      viewState.generateToken()
   }

}