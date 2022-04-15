package com.test.skb.ui.details

import com.test.skb.bus.RxBusSavedUpdate
import com.test.skb.dagger.ComponentManager
import com.test.skb.interactors.DetailsInteractor
import com.test.skb.repositories.server.model.Repo
import com.test.skb.ui.main.RepoModel
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class DetailsPresenter(private val pos: Int, saved: Boolean) : MvpPresenter<IDetailsMvpView>() {

   @Inject
   lateinit var router: Router

   @Inject
   lateinit var update: RxBusSavedUpdate

   private val interactor = DetailsInteractor()
   private val repo: Repo

   init {
      ComponentManager.instance.appComponent.inject(this)
      this.repo = this.interactor.getRepo(this.pos, saved)
   }

   override fun onFirstViewAttach() {
      super.onFirstViewAttach()
      viewState.setBinding(
         RepoModel().also { model ->
            repo.name.also { model.name = it }
            repo.owner.login.also { model.author = it }
            repo.owner.avatarUrl.also { model.authorImg = it }
            repo.description.also { model.description = it }
            repo.createdAt.also { model.createdBy = it.toString() }
            model.fork = repo.forks
            model.star = repo.stargazersCount
         }
      )
      viewState.setStarImg(this.interactor.isRepoSaved())
   }

   fun star() {
      viewState.setStarImg(!this.interactor.star())
      this.update.send(Any())
   }

   fun back() {
      this.router.exit()
   }

   fun isUserSignIn(): Boolean {
      return this.interactor.isUserSignIn()
   }
}