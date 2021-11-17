package com.fiqi.galleryapp.view.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqi.galleryapp.databinding.FragmentMainListBinding
import com.fiqi.galleryapp.view.adapters.main.ImageListAdapter
import com.fiqi.galleryapp.view.viewmodels.main.MainViewModel

class MainListFragment : Fragment() {

  private var _binding: FragmentMainListBinding? = null
  private val binding
    get() = _binding!!

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

    viewModel.getFailureMessage().observe(viewLifecycleOwner) {
      if (it != null) {
        showToast(it)
        viewModel.setFailureMessage(null)
      }
    }

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
    imageAdapter = ImageListAdapter { model ->
      findNavController().navigate(
        MainListFragmentDirections.actionNavMainListFragmentToNavViewItemFragment(model)
      )
    }

    binding.mainRvImage.apply {
      setHasFixedSize(true)
      adapter = imageAdapter
      layoutManager = GridLayoutManager(context, 2)
    }

    viewModel.getImages().observe(viewLifecycleOwner) { datas ->
      imageAdapter.changeList(datas)
    }
    viewModel.getImagesData()
  }

  private fun showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
