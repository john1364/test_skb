package com.test.skb.ui.main.search

import android.util.Log
import com.test.skb.Screens
import com.test.skb.dagger.ComponentManager
import com.test.skb.interactors.SearchInteractor
import com.test.skb.repositories.cache.ICacheRepositories
import com.test.skb.repositories.server.IServerRepositories
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SearchPresenter : MvpPresenter<ISearchMvpView>() {

   companion object {
      const val TAG = "SearchPresenter"
   }

   @Inject
   lateinit var serverRepositories: IServerRepositories

   @Inject
   lateinit var router: Router

   @Inject
   lateinit var cacheRepositories: ICacheRepositories

   private val disposable: CompositeDisposable = CompositeDisposable()
   private val interactor = SearchInteractor()
   private var lastSearch = ""
   private var nextPage = false

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun search(text: String) {
      val clear = this.lastSearch != text
      this.lastSearch = text

      if (!clear) {
         if (this.nextPage) {
            return
         }
         load(text, clear)
         this.nextPage = true
      } else {
         load(text, clear)
      }

      if (text.isEmpty()) {
         viewState.showStart()
      } else {
         viewState.showLoad()
      }
   }

   private fun load(text: String, clear: Boolean) {
      this.disposable.add(
         this.interactor.searchRepo(text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
               this.nextPage = false
               viewState.newData(it, clear)
            }) {
               Log.e(TAG, "search", it)
            }
      )
   }

   fun openRepo(pos: Int) {
      this.router.navigateTo(Screens.DetailsFragmentScreen(pos, false))
   }

   override fun onDestroy() {
      super.onDestroy()
      this.disposable.clear()
   }

   fun nextPage() {
      search(this.lastSearch)
   }
}