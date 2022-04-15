package com.test.skb.repositories.server.model

import com.google.gson.annotations.SerializedName

data class SearchRepo(
   @SerializedName("total_count")
   val totalCount: Int,
   @SerializedName("incomplete_results")
   val incompleteResults: Boolean,
   val items: ArrayList<Repo>
)

class Repo (
   var id: Int,
   var name: String,
   @SerializedName("total_count")
   var totalCount: Int = 0,
   var owner: Owner,
   var description: String,
   @SerializedName("created_at")
   var createdAt: Any,
   @SerializedName("stargazers_count")
   var stargazersCount: Int = 0,
   var forks: Int
)

data class Owner(
   var login: String,
   @SerializedName("avatar_url")
   var avatarUrl: String
)