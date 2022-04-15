package com.test.skb

import android.app.Application
import com.test.skb.dagger.ComponentManager


class SkbApplication : Application() {

   override fun onCreate() {
      ComponentManager.instance.initAppComponent(applicationContext)
      super.onCreate()
   }

}