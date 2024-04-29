package com.example.freshfeed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshfeed.MyApplication
import com.example.freshfeed.adapters.NewsAdapter
import com.example.freshfeed.api.Resource
import com.example.freshfeed.databinding.FragmentGeneralBinding
import com.example.freshfeed.models.NewsArticles
import com.example.freshfeed.models.TopHeadlines
import com.example.freshfeed.repo.Repository
import com.example.freshfeed.utils.MyUtils
import com.example.freshfeed.utils.NetworkConnection
import com.example.freshfeed.viewModels.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseNewsFragment : Fragment() {

    protected abstract val category: String

    private var binding: FragmentGeneralBinding? = null
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var myApplication: MyApplication
    private lateinit var repository: Repository
    lateinit var newsLiveData: MutableLiveData<Resource<TopHeadlines>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myApplication = requireActivity().application as MyApplication
        repository = myApplication.newsRepository
        initView()
        setObservers()
    }

    private fun initView() {
        newsLiveData = when (category) {
            "general" -> viewModel.generalNewsLiveData
            "business" -> viewModel.businessNewsLiveData
            "entertainment" ->  viewModel.entertainmentNewsLiveData
            "health" ->  viewModel.healthNewsLiveData
            "science" ->  viewModel.scienceNewsLiveData
            "sports" ->  viewModel.sportsNewsLiveData
            "technology" ->  viewModel.technologyNewsLiveData
            else -> throw IllegalArgumentException("Invalid category: $category")
        }
        newsAdapter = NewsAdapter()
        binding!!.newsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    private fun setObservers() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(requireActivity()) {
            if (it) {
                repository.deleteAllArticles(category)
                viewModel.getTopHeadlines(category)
            } else {
                newsLiveData.postValue(Resource.Loading())
                val dbArticles = repository.getAllArticles()
                val tempList = MyUtils.filterArticlesByCategory(dbArticles, category)
                if (dbArticles.isNotEmpty()) {
                    newsLiveData.postValue(
                        Resource.Success(
                            TopHeadlines(
                                "",
                                0,
                                tempList
                            )
                        )
                    )
                } else {
                    newsLiveData.postValue(Resource.Error("Internet not available"))
                }
            }
        }

        newsLiveData.observe(requireActivity()) { response ->
            when (response) {
                is Resource.Success -> {
                    binding!!.apply {
                        newsPB.visibility = View.GONE
                        errorTV.visibility = View.GONE
                        newsRV.visibility = View.VISIBLE
                    }
                    newsAdapter.setNewList(response.data!!.articles)
                    viewModel.viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            for (article in response.data.articles) {
                                repository.insertArticles(
                                    NewsArticles(
                                        article.id,
                                        article.source,
                                        article.author,
                                        article.title,
                                        article.description,
                                        article.url,
                                        article.urlToImage,
                                        article.publishedAt,
                                        article.content,
                                        category
                                    )
                                )
                            }
                        }
                    }
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
}
