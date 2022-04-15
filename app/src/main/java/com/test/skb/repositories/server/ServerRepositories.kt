package com.test.skb.repositories.server

import com.test.skb.repositories.cache.CacheRepositories
import com.test.skb.repositories.server.interceptor.BasicAuthInterceptor
import com.test.skb.repositories.server.model.Auth
import com.test.skb.repositories.server.model.Repo
import com.test.skb.repositories.server.model.SearchRepo
import com.test.skb.repositories.server.model.User
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ServerRepositories @Inject constructor() : IServerRepositories {

   companion object {
      const val BASE_URL = "https://api.github.com"
   }

   private var api: Api? = null
   var authData = Auth()

   init {
      val interceptor = HttpLoggingInterceptor()
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

      val okHttpClient = OkHttpClient.Builder()
         .addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header(
               "Accept",
               "application/vnd.github.v3+json"
            )
            chain.proceed(requestBuilder.build())
         })
         .addInterceptor(BasicAuthInterceptor(authData))
         .addInterceptor(interceptor)
         .connectTimeout(15, TimeUnit.SECONDS)
         .build()

      this.api = Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
         .client(okHttpClient)
         .baseUrl(BASE_URL)
         .build()
         .create(Api::class.java)
   }

   override fun getAuth(): Auth {
      return this.authData
   }

   override fun searchRepo(text: String, page: Int): Single<SearchRepo> {
      return this.api!!.searchRepository(
         text,
         page,
         CacheRepositories.PAGE_SIZE,
         null,
         null
      )
   }

   override fun authToken(): Single<User> {
      return this.api!!.users()
   }

   override fun getRepo(owner: String, repo: String): Single<Repo> {
      return this.api!!.getRepo(owner, repo)
   }

}