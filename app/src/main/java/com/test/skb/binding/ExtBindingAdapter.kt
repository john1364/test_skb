package com.test.skb.binding

import android.widget.ViewAnimator
import androidx.databinding.BindingAdapter


object ExtBindingAdapter {

   @JvmStatic
   @BindingAdapter("scene")
   fun showScene(animator: ViewAnimator, scene: Int) {
      animator.displayedChild = scene
   }

}