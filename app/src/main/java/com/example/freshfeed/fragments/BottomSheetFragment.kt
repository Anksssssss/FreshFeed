package com.example.freshfeed.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.freshfeed.databinding.BottomSheetBinding
import com.example.freshfeed.viewModels.NewsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: NewsViewModel
    private val PREFS_NAME = "MyPrefs"
    private val CATEGORY_KEY1 = "selected_category1"
    private val CATEGORY_KEY2 = "selected_category2"
    private val SOURCE_KEY = "selected_source"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadSavedSelections()
        binding.buttonSave.setOnClickListener {
            saveSelectionsToPrefs()
            dismiss()
        }
    }
    private fun saveSelectionsToPrefs() {
        val selectedCategory1 = binding.radioGroupCategory1.checkedRadioButtonId
        val selectedCategory2 = binding.radioGroupCategory2.checkedRadioButtonId
        val selectedSource = binding.radioGroupSource.checkedRadioButtonId

        val editor = sharedPreferences.edit()
        editor.putInt(CATEGORY_KEY1, selectedCategory1)
        editor.putInt(CATEGORY_KEY2, selectedCategory2)
        editor.putInt(SOURCE_KEY, selectedSource)
        editor.apply()
    }

    private fun loadSavedSelections() {
        val selectedCategory1 = sharedPreferences.getInt(CATEGORY_KEY1, -1)
        val selectedCategory2 = sharedPreferences.getInt(CATEGORY_KEY2, -1)
        val selectedSource = sharedPreferences.getInt(SOURCE_KEY, -1)

        if (selectedCategory1 != -1) {
            binding.radioGroupCategory1.check(selectedCategory1)
        }
        if (selectedCategory2 != -1) {
            binding.radioGroupCategory2.check(selectedCategory2)
        }
        if (selectedSource != -1) {
            binding.radioGroupSource.check(selectedSource)
        }
    }
}