package com.test.skb.ui.main.saved

import android.util.Log
import com.test.skb.Screens
import com.test.skb.bus.RxBusSavedUpdate
import com.test.skb.dagger.ComponentManager
import com.test.skb.interactors.SavedInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SavedPresenter : MvpPresenter<ISavedMvpView>() {

   @Inject
   lateinit var router: Router

   @Inject
   lateinit var update: RxBusSavedUpdate

   private val interactor = SavedInteractor()
   private val disposable: CompositeDisposable = CompositeDisposable()

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   override fun onFirstViewAttach() {
      super.onFirstViewAttach()

      getSavedRepo()

      this.disposable.add(
         this.update.waitCall()
            .subscribeOn(Schedulers.io())
            .subscribe({
               getSavedRepo()
            }) {
               Log.e("TAG", "onFirstViewAttach: ", it)
            }
      )
   }

   private fun getSavedRepo() {
      this.interactor.getSavedRepo()
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe({
            viewState.data(it)
         }) {
            Log.e("TAG", "onFirstViewAttach: ", it)
         }
   }

   fun openRepo(pos: Int) {
      this.router.navigateTo(Screens.DetailsFragmentScreen(pos, true))
   }

   fun search(text: String) {
      this.interactor.search(text)
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe({
            viewState.data(it)
         }) {
            Log.e("TAG", "search: ", it)
         }
   }

   override fun onDestroy() {
      super.onDestroy()
      this.disposable.clear()
   }
}