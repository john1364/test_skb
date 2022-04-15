package com.test.skb.ui.main.saved

import com.test.skb.ui.main.RepoModel
import moxy.MvpView

interface ISavedMvpView : MvpView {

   fun data(newData: List<RepoModel>)

}