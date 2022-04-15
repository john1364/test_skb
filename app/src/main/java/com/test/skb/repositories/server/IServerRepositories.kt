package com.test.skb.repositories.server

import com.test.skb.repositories.server.model.Auth
import com.test.skb.repositories.server.model.Repo
import com.test.skb.repositories.server.model.SearchRepo
import com.test.skb.repositories.server.model.User
import io.reactivex.rxjava3.core.Single

interface IServerRepositories {

   fun authToken(): Single<User>

   fun getRepo(owner: String, repo: String): Single<Repo>

   fun searchRepo(text: String, page: Int): Single<SearchRepo>

   fun getAuth(): Auth
}