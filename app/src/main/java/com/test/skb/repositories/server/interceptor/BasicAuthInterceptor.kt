package com.test.skb.repositories.server.interceptor

import com.test.skb.repositories.server.model.Auth
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthInterceptor(private val authData: Auth) : Interceptor {

   override fun intercept(chain: Interceptor.Chain): Response {
      val request: Request = chain.request()
      val authenticatedRequest: Request = request.newBuilder()
         .header(
            "Authorization",
            Credentials.basic(
               "",
               authData.token
            )
         )
         .build()
      return chain.proceed(authenticatedRequest)
   }

}