package com.fiqi.galleryapp.view.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqi.galleryapp.databinding.FragmentMainListBinding
import com.fiqi.galleryapp.view.adapters.main.ImageListAdapter
import com.fiqi.galleryapp.view.viewmodels.main.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainListFragment : Fragment() {

  private var _binding: FragmentMainListBinding? = null
  private val binding get() = _binding!!

  private val viewModel: MainViewModel by activityViewModels()

  private lateinit var imageAdapter: ImageListAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMainListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupButtons()
    setupList()
  }

  private fun setupButtons() {
    binding.mainFabAdd.setOnClickListener {
      findNavController().navigate(
        MainListFragmentDirections.actionNavMainListFragmentToNavAddItemFragment()
      )
    }
  }

  private fun setupList() {
    imageAdapter = ImageListAdapter()

    binding.mainRvImage.apply {
      setHasFixedSize(true)
      adapter = imageAdapter
      layoutManager = LinearLayoutManager(context)
    }

    viewModel.getImages().observe(viewLifecycleOwner) { datas ->
      imageAdapter.changeList(datas)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
