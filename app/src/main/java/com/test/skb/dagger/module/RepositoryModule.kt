package com.test.skb.dagger.module

import android.content.Context
import com.test.skb.repositories.cache.CacheRepositories
import com.test.skb.repositories.cache.ICacheRepositories
import com.test.skb.repositories.server.IServerRepositories
import com.test.skb.repositories.server.ServerRepositories
import com.test.skb.repositories.settings.ISettingsRepositories
import com.test.skb.repositories.settings.SettingsRepositories
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

   @Provides
   @Singleton
   fun provideSettingsRepository(context: Context): ISettingsRepositories {
      return SettingsRepositories(context)
   }

   @Provides
   @Singleton
   fun provideServerRepository(): IServerRepositories {
      return ServerRepositories()
   }

   @Provides
   @Singleton
   fun provideCacheRepository(): ICacheRepositories {
      return CacheRepositories()
   }

}