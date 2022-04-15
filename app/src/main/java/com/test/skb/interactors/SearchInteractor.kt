package com.test.skb.interactors

import com.test.skb.dagger.ComponentManager
import com.test.skb.repositories.cache.ICacheRepositories
import com.test.skb.repositories.server.IServerRepositories
import com.test.skb.ui.main.RepoModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchInteractor {

   @Inject
   lateinit var serverRepositories: IServerRepositories

   @Inject
   lateinit var cacheRepositories: ICacheRepositories

   private var lastSearch = ""

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun searchRepo(text: String): Single<List<RepoModel>> {
      if (this.lastSearch != text) {
         this.cacheRepositories.clear()
      }
      this.lastSearch = text
      return this.serverRepositories.searchRepo(text, this.cacheRepositories.getPage())
         .map { searchRepo ->
            val items = mutableListOf<RepoModel>()
            this.cacheRepositories.addData(searchRepo.totalCount, searchRepo.items)
            for (item in searchRepo.items) {
               items.add(
                  RepoModel()
                     .apply {
                        item.name.also { name = it }
                        item.owner.login.also { author = it }
                        item.owner.avatarUrl.also { authorImg = it }
                        item.description.also { description = it }
                        item.createdAt.also { createdBy = it.toString() }
                        fork = item.forks
                        star = item.stargazersCount
                     }
               )
            }
            items
         }
   }
}