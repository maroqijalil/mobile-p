package com.fiqi.galleryapp.view.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.fiqi.galleryapp.R
import com.fiqi.galleryapp.databinding.FragmentAddItemBinding
import com.fiqi.galleryapp.view.viewmodels.main.MainViewModel

class AddItemFragment : Fragment() {

  private var _binding: FragmentAddItemBinding? = null
  private val binding get() = _binding!!

  private val viewModel: MainViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentAddItemBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}