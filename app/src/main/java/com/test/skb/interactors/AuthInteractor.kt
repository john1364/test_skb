package com.test.skb.interactors

import com.test.skb.dagger.ComponentManager
import com.test.skb.repositories.cache.ICacheRepositories
import com.test.skb.repositories.server.IServerRepositories
import com.test.skb.repositories.server.model.Auth
import com.test.skb.repositories.server.model.User
import com.test.skb.repositories.settings.ISettingsRepositories
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthInteractor {

   @Inject
   lateinit var serverRepositories: IServerRepositories

   @Inject
   lateinit var settingsRepositories: ISettingsRepositories

   @Inject
   lateinit var cacheRepositories: ICacheRepositories

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getAuth(): Auth {
      return this.serverRepositories.getAuth()
   }

   fun isAuth(): Boolean {
      return this.settingsRepositories.getToken().isNotEmpty()
   }

   fun authToken(): Single<User> {
      this.serverRepositories.getAuth().scene = 1
      return this.serverRepositories.authToken()
         .map { user ->
            this.serverRepositories.getAuth().scene = 0
            this.cacheRepositories.setUser(user)
            this.settingsRepositories.apply {
               saveToken(this@AuthInteractor.serverRepositories.getAuth().token)
               setUser(user.login!!)
               if (getUUIDUser().isEmpty()) {
                  saveUUIDUser()
               }
            }
            user
         }.doOnError {
            this.serverRepositories.getAuth().scene = 0
         }
   }

}