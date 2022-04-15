package com.test.skb.interactors

import com.test.skb.dagger.ComponentManager
import com.test.skb.repositories.cache.ICacheRepositories
import com.test.skb.repositories.settings.ISettingsRepositories
import javax.inject.Inject

class MainInteractor {

   @Inject
   lateinit var cacheRepositories: ICacheRepositories

   @Inject
   lateinit var settingsRepositories: ISettingsRepositories

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun isUserSignIn(): Boolean {
      return this.settingsRepositories.getToken().isNotEmpty()
   }

   fun exit() {
      this.settingsRepositories.signOut()
      this.cacheRepositories.setUser(null)
   }

   fun clearSaved() {
      this.cacheRepositories.clearSaved()
      this.settingsRepositories.clearSaved()
   }
}