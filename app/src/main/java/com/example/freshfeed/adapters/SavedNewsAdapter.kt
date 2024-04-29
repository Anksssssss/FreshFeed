package com.example.freshfeed.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freshfeed.R
import com.example.freshfeed.databinding.NewsItemBinding
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.SavedArticles

class SavedNewsAdapter(
    private val listener: RecyclerViewEvent
): RecyclerView.Adapter<SavedNewsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener,View.OnLongClickListener{
        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }


        fun bind(position: Int){
            Glide.with(itemView)
                .load(newsList[position].image)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(binding.newsImage)

            binding.newsTitle.text = newsList[position].title
            binding.newsSource.text = buildString {
                append("src: ")
                append(newsList[position].source)
            }
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(newsList[position])
            }
        }

        override fun onLongClick(v: View): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLongItemClick(v,newsList[position])
            }
            return true
        }

    }

    private var newsList = emptyList<SavedArticles>()

    fun setNewList(newList:List<SavedArticles>){
        this.newsList = newList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    interface RecyclerViewEvent{
        fun onItemClick(article: SavedArticles)
        fun onLongItemClick(view:View, article:SavedArticles)
    }

}