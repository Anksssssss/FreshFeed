package com.example.freshfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.freshfeed.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    lateinit var binding: ActivitySummaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setListeners()
    }

    private fun setListeners() {
        binding.backBtn.setOnClickListener {
            supportFragmentManager.popBackStack()
            finish()
        }
    }

    private fun initView() {
        val extras = intent.extras
        if(extras!=null){
            val title = extras.getString("title")?:"NA"
            val image = extras.getString("image")
            val description = extras.getString("description")?:"NA"
            Glide.with(this)
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.placeholder) // Placeholder image resource
                .error(R.drawable.error_image) // Error image resource
                .into(binding.newsImage)
            binding.apply {
                newsHeading.text = title
                newsDesc.text = description
            }
        }
    }
}