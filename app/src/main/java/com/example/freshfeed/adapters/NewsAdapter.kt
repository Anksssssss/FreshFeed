package com.example.freshfeed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freshfeed.R
import com.example.freshfeed.databinding.NewsItemBinding
import com.example.freshfeed.models.Article

class NewsAdapter(
    private val listener: RecyclerViewEvent
): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
           init {
               binding.root.setOnClickListener(this)
           }

            fun bind(position: Int){
                Glide.with(itemView)
                    .load(newsList[position].urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_image)
                    .into(binding.newsImage)

                    binding.newsTitle.text = newsList[position].title
                    binding.newsSource.text = buildString {
                    append("src: ")
                    append(newsList[position].source!!.name)
                }
            }
            override fun onClick(v: View?) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(newsList[position])
                }
            }
    }

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
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    interface RecyclerViewEvent{
        fun onItemClick(article:Article)
    }

}