package com.test.skb.repositories.server

import com.test.skb.repositories.server.model.Repo
import com.test.skb.repositories.server.model.SearchRepo
import com.test.skb.repositories.server.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

   @GET("search/repositories")
   fun searchRepository(
      @Query("q") searchText: String,
      @Query("page") page: Int?,
      @Query("per_page") per_page: Int?,
      @Query("sort") sort: String?,
      @Query("order") order: String?
   ): Single<SearchRepo>

   @GET("user")
   @Headers("Accept: application/vnd.github.v3+json")
   fun users(): Single<User>

   @GET("repos/{owner}/{repo}")
   @Headers("Accept: application/vnd.github.v3+json")
   fun getRepo(
      @Path("owner") owner: String,
      @Path("repo") repo: String
   ): Single<Repo>

}