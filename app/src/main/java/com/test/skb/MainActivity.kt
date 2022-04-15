package com.test.skb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.test.skb.dagger.ComponentManager
import com.test.skb.repositories.settings.ISettingsRepositories
import org.jetbrains.annotations.NotNull
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

   @Inject
   lateinit var navigatorHolder: NavigatorHolder

   @Inject
   lateinit var router: Router

   @Inject
   lateinit var settingsRepositories: ISettingsRepositories

   private val navigator: Navigator =
      object : SupportAppNavigator(this, supportFragmentManager, R.id.root) {
         override fun applyCommands(@NotNull commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
         }
      }

   override fun onCreate(savedInstanceState: Bundle?) {
      ComponentManager.instance.appComponent.inject(this)
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)

      this.router.newRootScreen(Screens.AuthFragmentScreen())
   }

   override fun onResumeFragments() {
      super.onResumeFragments()
      this.navigatorHolder.setNavigator(navigator)
   }

   override fun onPause() {
      this.navigatorHolder.removeNavigator()
      super.onPause()
   }

}