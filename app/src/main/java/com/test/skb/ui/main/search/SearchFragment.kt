package com.test.skb.ui.main.search

import android.os.Bundle
import android.view.*
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


class SearchFragment : MvpAppCompatFragment(), ISearchMvpView {

   @InjectPresenter
   lateinit var presenter: SearchPresenter
   private var binding: ViewDataBinding? = null
   private var recyclerView: RecyclerView? = null
   private var adapter: SearchAdapter? = null
   private var search: SearchView? = null

   private val disposable = CompositeDisposable()

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

      val view = this.binding!!.root
      this.recyclerView = view.findViewById(R.id.recycler_search)

      return this.binding!!.root
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
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .switchMapCompletable {
               this.presenter.search(it)
               Completable.complete()
            }
            .subscribe()
      )
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
      this.recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val totalItem: Int = recyclerView.layoutManager!!.itemCount
            val lastVisibleItem: Int =
               (recyclerView.layoutManager!! as LinearLayoutManager).findLastVisibleItemPosition()
            if (totalItem - lastVisibleItem <= 10) {
               this@SearchFragment.presenter.nextPage()
            }
         }
      })
   }

   override fun newData(newData: List<RepoModel>, clear: Boolean) {
      this.binding!!.root.findViewById<ViewAnimator>(R.id.animatorSearch).displayedChild =
         if (newData.isEmpty()) {
            SearchAnimator.EMPTY.pos
         } else {
            SearchAnimator.LIST.pos
         }
      this.adapter?.data(newData, clear)
   }

   override fun showLoad() {
      this.binding!!.root.findViewById<ViewAnimator>(R.id.animatorSearch)
         .displayedChild = SearchAnimator.LOAD.pos
   }

   override fun showStart() {
      this.binding!!.root.findViewById<ViewAnimator>(R.id.animatorSearch)
         .displayedChild = SearchAnimator.START.pos
   }

}