package com.example.freshfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.freshfeed.databinding.ActivitySummaryBinding
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.SavedArticles
import com.example.freshfeed.repo.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding
    private lateinit var myApplication: MyApplication
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myApplication = application as MyApplication
        repository = myApplication.newsRepository
        setSupportActionBar(binding.toolbar)
        initView()
        setListeners()
    }

    private fun setListeners() {
//        binding.backBtn.setOnClickListener {
//            supportFragmentManager.popBackStack()
//            finish()
//        }
    }

    private fun initView() {
            Glide.with(this)
                .load(intent.getStringExtra("image"))
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(binding.newsImage)
            binding.apply {
                newsHeading.text = intent.getStringExtra("title")
                newsDesc.text = intent.getStringExtra("description")
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.saved -> {
                saveArticle()
                Toast.makeText(this,"Article Saved",Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveArticle() {
        val extras = intent.extras!!
        val savedArticle = SavedArticles(
            title = extras.getString("title")?:"NA",
            description = extras.getString("description"),
            image = extras.getString("image"),
            source = extras.getString("source")
        )

        CoroutineScope(Dispatchers.IO).launch {
            repository.insertSavedArticle(savedArticle)
        }
    }
}
