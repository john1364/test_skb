package com.test.skb.dagger.module

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class NavigationModule {

   private var cicerone: Cicerone<Router>? = null

   init {
      this.cicerone = Cicerone.create()
   }

   @Provides
   @Singleton
   fun provideRouter(): Router {
      return this.cicerone!!.router
   }

   @Provides
   @Singleton
   fun provideNavigatorHolder(): NavigatorHolder {
      return this.cicerone!!.navigatorHolder
   }
}