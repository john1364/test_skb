package com.test.skb.ui.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ViewAnimator
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.skb.R
import com.test.skb.ui.main.MainFragment.Companion.SEARCH_DELAY
import com.test.skb.ui.main.RepoModel
import com.test.skb.ui.main.SearchAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import java.util.concurrent.TimeUnit

class SavedFragment : MvpAppCompatFragment(), ISavedMvpView {

   @InjectPresenter
   lateinit var presenter: SavedPresenter
   private var binding: ViewDataBinding? = null
   private var animator: ViewAnimator? = null
   private var recyclerView: RecyclerView? = null
   private var adapter: SearchAdapter? = null
   private var search: SearchView? = null

   private val disposable = CompositeDisposable()

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false)
      val view = this.binding!!.root
      this.animator = view.findViewById(R.id.animatorSaved)
      this.recyclerView = view.findViewById(R.id.recycler_search)
      return this.binding!!.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      this.search =
         requireActivity().findViewById<Toolbar>(R.id.toolbar)
            .menu.findItem(R.id.search).actionView as SearchView

      this.adapter = SearchAdapter {
         this.presenter.openRepo(it)
      }
      this.recyclerView!!.layoutManager = LinearLayoutManager(requireActivity())
      this.recyclerView!!.adapter = this.adapter
   }

   override fun data(newData: List<RepoModel>) {
      this.animator?.displayedChild =
         if (newData.isEmpty()) {
            2
         } else {
            1
         }
      this.adapter?.data(newData, true)
   }

   override fun onPause() {
      super.onPause()
      this.search?.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)?.setText("")
      this.disposable.clear()
   }

   override fun onResume() {
      super.onResume()
      search()
   }

   private fun search() {
      this.disposable.add(
         Observable.create(ObservableOnSubscribe<String> {
            this.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextChange(newText: String?): Boolean {
                  it.onNext(newText!!.trim())
                  return false
               }

               override fun onQueryTextSubmit(query: String?): Boolean {
                  return true
               }
            })
         }).debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .switchMapCompletable {
               this.presenter.search(it)
               Completable.complete()
            }
            .subscribe()
      )
   }

}