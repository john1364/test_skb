package com.test.skb.interactors

import com.test.skb.dagger.ComponentManager
import com.test.skb.repositories.cache.ICacheRepositories
import com.test.skb.repositories.server.model.Repo
import com.test.skb.repositories.settings.ISettingsRepositories
import javax.inject.Inject

class DetailsInteractor {

   @Inject
   lateinit var cacheRepositories: ICacheRepositories

   @Inject
   lateinit var settingsRepositories: ISettingsRepositories

   private var repo: Repo? = null

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getRepo(pos: Int, saved: Boolean): Repo {
      this.repo = if (saved) {
         this.cacheRepositories.getRepoSaved(pos)
      } else {
         this.cacheRepositories.getRepo(pos)
      }

      return this.repo!!
   }

   fun isRepoSaved(): Boolean {
      return this.settingsRepositories.isRepoOnSaved(this.repo?.owner?.login, this.repo?.name)
   }

   fun star(): Boolean {
      val owner = this.repo?.owner?.login
      val repoName = this.repo?.name
      val saved = isRepoSaved()
      if (isRepoSaved()) {
         this.settingsRepositories.removeRepoOnSaved(owner, repoName)
      } else {
         this.settingsRepositories.addRepoToSaved(owner, repoName)
      }
      return saved
   }

   fun isUserSignIn(): Boolean {
      return this.cacheRepositories.getUser() != null
   }

}