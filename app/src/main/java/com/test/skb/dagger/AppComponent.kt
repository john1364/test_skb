package com.test.skb.dagger

import android.content.Context
import com.test.skb.MainActivity
import com.test.skb.dagger.module.NavigationModule
import com.test.skb.dagger.module.RepositoryModule
import com.test.skb.interactors.*
import com.test.skb.repositories.settings.ISettingsRepositories
import com.test.skb.ui.auth.AuthPresenter
import com.test.skb.ui.details.DetailsPresenter
import com.test.skb.ui.main.MainPresenter
import com.test.skb.ui.main.saved.SavedPresenter
import com.test.skb.ui.main.search.SearchPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class, RepositoryModule::class])
interface AppComponent {

   @Component.Factory
   interface Factory {
      fun create(@BindsInstance context: Context): AppComponent
   }

   fun inject(classes: AuthPresenter)
   fun inject(classes: MainActivity)
   fun inject(classes: SearchPresenter)
   fun inject(classes: DetailsPresenter)
   fun inject(classes: MainPresenter)
   fun inject(classes: ISettingsRepositories)
   fun inject(classes: SavedPresenter)
   fun inject(classes: SearchInteractor)
   fun inject(classes: MainInteractor)
   fun inject(classes: DetailsInteractor)
   fun inject(classes: SavedInteractor)
   fun inject(classes: AuthInteractor)

}