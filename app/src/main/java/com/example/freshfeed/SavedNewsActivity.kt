package com.example.freshfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshfeed.adapters.NewsAdapter
import com.example.freshfeed.adapters.SavedNewsAdapter
import com.example.freshfeed.api.Resource
import com.example.freshfeed.databinding.ActivitySavedNewsBinding
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.SavedArticles
import com.example.freshfeed.repo.Repository
import com.example.freshfeed.viewModels.NewsViewModel
import com.example.freshfeed.viewModels.SavedNewsViewModel

class SavedNewsActivity : AppCompatActivity(),SavedNewsAdapter.RecyclerViewEvent {

    private lateinit var binding: ActivitySavedNewsBinding
    private lateinit var viewModel: SavedNewsViewModel
    private lateinit var newsAdapter: SavedNewsAdapter
    private lateinit var myApplication: MyApplication
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SavedNewsViewModel::class.java)
        myApplication = this.application as MyApplication
        repository = myApplication.newsRepository
        viewModel.repository = repository

        initView()
        setObservers()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        viewModel.getSavedNewsArticles()
        newsAdapter = SavedNewsAdapter(this@SavedNewsActivity)
        binding!!.newsRV.apply {
            layoutManager = LinearLayoutManager(this@SavedNewsActivity)
            adapter = newsAdapter
        }
    }

    private fun setObservers() {
        viewModel.savedNewsLiveData.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    binding!!.apply {
                        newsPB.visibility = View.GONE
                        errorTV.visibility = View.GONE
                        newsRV.visibility = View.VISIBLE
                    }
                    newsAdapter.setNewList(response.data!!)
                }
                is Resource.Loading -> {
                    binding!!.apply {
                        newsPB.visibility = View.VISIBLE
                        errorTV.visibility = View.GONE
                        newsRV.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    binding!!.apply {
                        errorTV.text = response.message
                        newsPB.visibility = View.GONE
                        errorTV.visibility = View.VISIBLE
                        newsRV.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onItemClick(article: SavedArticles) {
        val bundle = Bundle().apply {
            putString("image", article.image)
            putString("title",article.title)
            putString("description",article.description)
            putString("source",article.source)
            putString("url",article.url)
        }
        val intent = Intent(this, SummaryActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onLongItemClick(view:View, article: SavedArticles) {
        val popupMenu = PopupMenu(this, view, Gravity.END)
        popupMenu.menuInflater.inflate(R.menu.delete_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    viewModel.deleteSavedArticle(article)
                    viewModel.getSavedNewsArticles()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

}