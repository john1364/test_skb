package com.test.skb.repositories.settings

interface ISettingsRepositories {

   fun setUser(login: String)

   fun signOut()

   fun saveUUIDUser()

   fun getUUIDUser(): String

   fun saveToken(token: String)

   fun getToken(): String

   fun addRepoToSaved(owner: String?, repoName: String?)

   fun isRepoOnSaved(owner: String?, repoName: String?): Boolean

   fun getSavedRepo(): MutableSet<String>?

   fun removeRepoOnSaved(owner: String?, repoName: String?)

   fun clearSaved()

}