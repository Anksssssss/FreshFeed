package com.example.freshfeed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freshfeed.R
import com.example.freshfeed.databinding.NewsItemBinding
import com.example.freshfeed.models.Article

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var newsList = emptyList<Article>()

    fun setNewList(newList:List<Article>){
        this.newsList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(newsList[position].urlToImage)
            .centerCrop()
            .placeholder(R.drawable.placeholder) // Placeholder image resource
            .error(R.drawable.error_image) // Error image resource
            .into(holder.binding.newsImage)

        holder.binding.newsTitle.text = newsList[position].title
        holder.binding.newsSource.text = buildString {
            append("src: ")
            append(newsList[position].source!!.name)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


}