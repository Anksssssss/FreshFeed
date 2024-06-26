package com.example.freshfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshfeed.adapters.NewsAdapter
import com.example.freshfeed.api.Resource
import com.example.freshfeed.databinding.ActivitySearchBinding
import com.example.freshfeed.models.Article
import com.example.freshfeed.repo.Repository
import com.example.freshfeed.viewModels.NewsViewModel
import com.example.freshfeed.viewModels.SearchViewModel

class SearchActivity : AppCompatActivity(), NewsAdapter.RecyclerViewEvent {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var myApplication: MyApplication
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        myApplication = this.application as MyApplication
        repository = myApplication.newsRepository
        viewModel.repository = repository
        initView()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.searchNewsLiveData.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    binding!!.apply {
                        newsPB.visibility = View.GONE
                        errorTV.visibility = View.GONE
                        newsRV.visibility = View.VISIBLE
                    }
                    newsAdapter.setNewList(response.data!!.articles)
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
    private fun setListeners() {
        binding.btnSearch.setOnClickListener {
            val query = binding.editText.text.toString()
            if(query.isNotEmpty()){
                viewModel.searchNews(query)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editText.windowToken, 0)
                binding.editText.clearFocus()
            }else{
                Toast.makeText(this,"Please enter something",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        binding.editText.requestFocus()
        newsAdapter = NewsAdapter(this)
        binding!!.newsRV.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = newsAdapter
        }
    }

    override fun onItemClick(article: Article) {
        val bundle = Bundle().apply {
            putString("image", article.urlToImage)
            putString("title",article.title)
            putString("description",article.description)
            putString("source",article.source?.name)
            putString("url",article.url)
        }
        val intent = Intent(this, SummaryActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}