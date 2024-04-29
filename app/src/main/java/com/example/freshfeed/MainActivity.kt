package com.example.freshfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.freshfeed.adapters.FragmentPageAdapter
import com.example.freshfeed.api.Resource
import com.example.freshfeed.databinding.ActivityMainBinding
import com.example.freshfeed.models.TopHeadlines
import com.example.freshfeed.repo.Repository
import com.example.freshfeed.utils.NetworkConnection
import com.example.freshfeed.viewModels.NewsViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var myApplication: MyApplication
    private lateinit var repository: Repository

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        myApplication = this.application as MyApplication
        repository = myApplication.newsRepository
        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        initView()
    }
    private fun initView() {
        tabLayout.apply {
            addTab(tabLayout.newTab().setText("General"))
            addTab(tabLayout.newTab().setText("Business"))
            addTab(tabLayout.newTab().setText("Entertainment"))
            addTab(tabLayout.newTab().setText("Health"))
            addTab(tabLayout.newTab().setText("Science"))
            addTab(tabLayout.newTab().setText("Sports"))
            addTab(tabLayout.newTab().setText("Technology"))
        }
        viewPager2.adapter = adapter
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
        if(tab!=null){
            viewPager2.currentItem = tab.position
        }
        }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        tabLayout.selectTab((tabLayout.getTabAt(position)))
    }
})
    }

}