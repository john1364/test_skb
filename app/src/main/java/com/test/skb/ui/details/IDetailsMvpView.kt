package com.test.skb.ui.details

import com.test.skb.ui.main.RepoModel
import moxy.MvpView

interface IDetailsMvpView : MvpView {

   fun setBinding(model: RepoModel)

   fun setStarImg(saved: Boolean)

}