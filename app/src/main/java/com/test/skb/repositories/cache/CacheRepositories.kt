package com.test.skb.repositories.cache

import com.test.skb.repositories.server.model.Repo
import com.test.skb.repositories.server.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheRepositories @Inject constructor() : ICacheRepositories {

   companion object {
      const val PAGE_SIZE = 30
   }

   private var user: User? = null
   private var total: Int? = null
   private var page = 1
   private val items: ArrayList<Repo> = ArrayList()
   private val itemsSaved: ArrayList<Repo> = ArrayList()

   override fun setUser(user: User?) {
      this.user = user
   }

   override fun getUser(): User? {
      return this.user
   }

   override fun addData(total: Int, newData: ArrayList<Repo>) {
      this.total = total
      this.items.addAll(newData)
      this.page++
   }

   override fun addDataSaved(newData: ArrayList<Repo>) {
      this.itemsSaved.addAll(newData)
   }

   override fun clear() {
      this.total = 0
      this.page = 1
      this.items.clear()
   }

   override fun clearSaved() {
      this.itemsSaved.clear()
   }

   override fun getPage(): Int {
      return this.page
   }

   override fun getRepo(pos: Int): Repo {
      return this.items[pos]
   }

   override fun getRepoSaved(pos: Int): Repo {
      return this.itemsSaved[pos]
   }

   override fun getReposSaved(): ArrayList<Repo> {
      return this.itemsSaved
   }

}