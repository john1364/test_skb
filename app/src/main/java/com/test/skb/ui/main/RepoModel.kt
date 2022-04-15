package com.test.skb.ui.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class RepoModel : BaseObservable() {

   @get:Bindable
   var name: String = ""
      set(value) {
         field = value
         notifyPropertyChanged(BR.name)
      }

   @get:Bindable
   var author: String = ""
      set(value) {
         field = value
         notifyPropertyChanged(BR.author)
      }

   @get:Bindable
   var authorImg: String = ""
      set(value) {
         field = value
         notifyPropertyChanged(BR.authorImg)
      }

   @get:Bindable
   var description: String = ""
      set(value) {
         field = value
         notifyPropertyChanged(BR.description)
      }

   @get:Bindable
   var fork: Int = 0
      set(value) {
         field = value
         notifyPropertyChanged(BR.fork)
      }

   @get:Bindable
   var star: Int = 0
      set(value) {
         field = value
         notifyPropertyChanged(BR.star)
      }

   @get:Bindable
   var createdBy: String = ""
      set(value) {
         field = value
         notifyPropertyChanged(BR.createdBy)
      }

}