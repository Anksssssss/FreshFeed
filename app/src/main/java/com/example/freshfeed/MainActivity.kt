package com.example.freshfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.freshfeed.adapters.FragmentPageAdapter
import com.example.freshfeed.databinding.ActivityMainBinding
import com.example.freshfeed.fragments.BottomSheetFragment
import com.example.freshfeed.repo.Repository
import com.example.freshfeed.viewModels.NewsViewModel
import com.google.android.material.tabs.TabLayout

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
        setSupportActionBar(binding.toolbar)
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
        }}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab((tabLayout.getTabAt(position)))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                //Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.saved -> {
                //Toast.makeText(this,"Save",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SavedNewsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.settings->{
                val bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}