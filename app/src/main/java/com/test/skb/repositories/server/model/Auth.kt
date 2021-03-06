package com.test.skb.repositories.server.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class Auth : BaseObservable(){

   @get:Bindable
   var scene: Int = 0
      set(value) {
         field = value
         notifyPropertyChanged(BR.scene)
      }

   @get:Bindable
   var token: String = ""
      set(value) {
         field = value
         notifyPropertyChanged(BR.token)
      }

}