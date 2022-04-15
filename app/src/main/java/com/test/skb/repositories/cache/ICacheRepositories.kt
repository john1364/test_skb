package com.test.skb.repositories.cache

import com.test.skb.repositories.server.model.Repo
import com.test.skb.repositories.server.model.User

interface ICacheRepositories {

   fun addData(total: Int, newData: ArrayList<Repo>)

   fun clear()

   fun getPage(): Int

   fun getRepo(pos: Int): Repo

   fun getUser(): User?

   fun setUser(user: User?)

   fun addDataSaved(newData: ArrayList<Repo>)

   fun getRepoSaved(pos: Int): Repo

   fun clearSaved()

   fun getReposSaved(): ArrayList<Repo>

}