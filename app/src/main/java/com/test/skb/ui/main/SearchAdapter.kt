package com.test.skb.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.skb.R


internal class SearchAdapter(private val click: (pos: Int) -> Unit) : RecyclerView.Adapter<Data>() {

   private val items = mutableListOf<RepoModel>()

   @SuppressLint("NotifyDataSetChanged")
   fun addData(newData: List<RepoModel>, clear: Boolean) {
      if (clear) {
         this.items.clear()
      }
      this.items.addAll(newData)
      notifyDataSetChanged()
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Data {
      val inflater = LayoutInflater.from(parent.context)
      return Data(
         DataBindingUtil.inflate(
            inflater,
            R.layout.repo_item,
            parent,
            false
         )
      )
   }

   override fun onBindViewHolder(holder: Data, position: Int) {
      val model = this.items[position]

      if (model.authorImg.isNotEmpty()) {
         Glide.with(holder.itemView.context)
            .load(model.authorImg)
            .override(60, 60)
            .into(holder.itemView.findViewById(R.id.imageView2))
      }

      holder.bind(model)
      holder.itemView.setOnClickListener {
         this.click.invoke(position)
      }
   }

   override fun getItemCount(): Int {
      return this.items.size
   }

}

internal class Data(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(
   binding.root
) {
   fun bind(data: RepoModel) {
      this.binding.setVariable(BR.model, data)
      this.binding.executePendingBindings()
   }
}