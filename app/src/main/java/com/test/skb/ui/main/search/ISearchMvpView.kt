package com.test.skb.ui.main.search

import com.test.skb.ui.main.RepoModel
import moxy.MvpView

interface ISearchMvpView : MvpView {

   fun newData(newData: List<RepoModel>, clear: Boolean)

   fun showLoad()

   fun showStart()

}