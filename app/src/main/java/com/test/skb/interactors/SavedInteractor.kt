package com.test.skb.interactors

import com.test.skb.dagger.ComponentManager
import com.test.skb.repositories.cache.ICacheRepositories
import com.test.skb.repositories.server.IServerRepositories
import com.test.skb.repositories.server.model.Repo
import com.test.skb.repositories.settings.ISettingsRepositories
import com.test.skb.ui.main.RepoModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class SavedInteractor {

   @Inject
   lateinit var serverRepositories: IServerRepositories

   @Inject
   lateinit var settingsRepositories: ISettingsRepositories

   @Inject
   lateinit var cacheRepositories: ICacheRepositories

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getSavedRepo(): Single<MutableList<RepoModel>> {
      return Observable.fromIterable(this.settingsRepositories.getSavedRepo() ?: emptyList())
         .flatMap {
            val owner = it.substring(0, it.indexOf("/"))
            val repo = it.substring(it.indexOf("/") + 1)
            return@flatMap this.serverRepositories.getRepo(owner, repo)
               .toObservable()
         }
         .toList()
         .map { listRepo ->
            this.cacheRepositories.addDataSaved(listRepo as ArrayList<Repo>)
            createRepoList(listRepo)
         }
   }

   fun search(text: String): Observable<MutableList<RepoModel>> {
      return Observable.fromIterable(this.cacheRepositories.getReposSaved())
         .filter { repo ->
            repo.name.lowercase().contains(text.lowercase())
         }
         .toList()
         .map { createRepoList(it) }
         .toObservable()
   }

   private fun createRepoList(listRepo: MutableList<Repo>): MutableList<RepoModel> {
      val items = mutableListOf<RepoModel>()
      for (item in listRepo) {
         items.add(createRepoModel(item))
      }
      return items
   }

   private fun createRepoModel(item: Repo): RepoModel {
      return RepoModel()
         .apply {
            item.name.also { name = it }
            item.owner.login.also { author = it }
            item.owner.avatarUrl.also { authorImg = it }
            item.description.also { description = it }
            item.createdAt.also { createdBy = it.toString() }
            fork = item.forks
            star = item.stargazersCount
         }
   }

}