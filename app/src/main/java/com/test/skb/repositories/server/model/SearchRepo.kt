package com.test.skb.repositories.server.model

class SearchRepo {

   val total_count: Int? = null
   val incomplete_results: Boolean? = null
   val items = ArrayList<Repo>()

}

class Repo {
   var id: Int = -1
   var name: String? = null
   var total_count: Int = 0
   var owner: Owner? = null
   var description: String? = null
   var created_at: Any? = null
   var stargazers_count: Int = 0
   var forks: Int = 0
}

class Owner {
   var login: String? = null
   var avatar_url: String? = null
}