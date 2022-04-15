package com.test.skb.repositories.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsRepositories @Inject constructor(private val context: Context) :
   ISettingsRepositories {

   companion object {
      const val SAVED_REPO = "savedRepo"
      const val KEY_UUID = "uuidUser"
      const val TOKEN = "token"
   }

   private var preferences: SharedPreferences? = null
   private var login: String? = null

   init {
      this.preferences = PreferenceManager.getDefaultSharedPreferences(this.context)
   }

   override fun setUser(login: String) {
      this.login = login
   }

   override fun signOut() {
      this.login = null
      this.preferences?.edit()
         ?.putString(TOKEN, null)
         ?.apply()
   }

   override fun saveUUIDUser() {
      this.preferences?.edit()
         ?.putString(getKeyUUID(), UUID.randomUUID().toString())
         ?.apply()
   }

   override fun getUUIDUser(): String {
      return this.preferences?.getString(getKeyUUID(), "") ?: ""
   }

   override fun saveToken(token: String) {
      this.preferences?.edit()?.putString(TOKEN, token)?.apply()
   }

   override fun getToken(): String {
      return this.preferences?.getString(TOKEN, "") ?: ""
   }

   override fun addRepoToSaved(owner: String?, repoName: String?) {
      val savedRepo: MutableSet<String>? =
         this.preferences?.getStringSet(getKeyUUIDForSavedRepo(), mutableSetOf())
      val sets = mutableSetOf<String>()
      savedRepo?.also {
         sets.addAll(it)
      }
      sets.add("$owner/$repoName")

      this.preferences?.edit()?.putStringSet(getKeyUUIDForSavedRepo(), sets)?.apply()
   }

   override fun isRepoOnSaved(owner: String?, repoName: String?): Boolean {
      val savedRepo = this.preferences?.getStringSet(getKeyUUIDForSavedRepo(), mutableSetOf())
      return savedRepo?.contains("$owner/$repoName") ?: false
   }

   override fun getSavedRepo(): MutableSet<String>? {
      return this.preferences?.getStringSet(getKeyUUIDForSavedRepo(), mutableSetOf())
   }

   override fun removeRepoOnSaved(owner: String?, repoName: String?) {
      val savedRepo: MutableSet<String>? =
         this.preferences?.getStringSet(getKeyUUIDForSavedRepo(), mutableSetOf())
      val sets = mutableSetOf<String>()
      savedRepo?.also {
         sets.addAll(it)
      }
      sets.remove("$owner/$repoName")

      this.preferences?.edit()?.putStringSet(getKeyUUIDForSavedRepo(), sets)?.apply()
   }

   override fun clearSaved() {
      this.preferences?.edit()?.putStringSet(getKeyUUIDForSavedRepo(), null)?.apply()
   }

   private fun getKeyUUIDForSavedRepo(): String {
      return "${SAVED_REPO}_${getUUIDUser()}"
   }

   private fun getKeyUUID(): String {
      return "${KEY_UUID}_${this.login}"
   }

}