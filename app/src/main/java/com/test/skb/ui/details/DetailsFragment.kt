package com.test.skb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.test.skb.R
import com.test.skb.ui.main.RepoModel
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class DetailsFragment : MvpAppCompatFragment(), IDetailsMvpView {

   @InjectPresenter
   lateinit var presenter: DetailsPresenter
   private var binding: ViewDataBinding? = null
   private var star: FloatingActionButton? = null

   @ProvidePresenter
   fun provide(): DetailsPresenter {
      val pos = arguments?.getInt("pos")!!
      val saved = arguments?.getBoolean("saved")!!
      return DetailsPresenter(pos, saved)
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      this.binding =
         DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
      return this.binding!!.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      this.star = this.binding?.root?.findViewById(R.id.star)
      if (this.presenter.isUserSignIn()) {
         this.star?.setOnClickListener {
            this.presenter.star()
         }
      } else {
         this.star?.visibility = GONE
      }
      view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
         this.presenter.back()
      }
   }

   override fun setBinding(model: RepoModel) {
      this.binding!!.setVariable(BR.model, model)
      Glide.with(this)
         .load(model.authorImg)
         .override(150, 150)
         .into(this.binding!!.root.findViewById(R.id.imageView2))
   }

   override fun setStarImg(saved: Boolean) {
      this.star?.setImageDrawable(
         if (saved) {
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_star)
         } else {
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_star_outline)
         }
      )
   }

}