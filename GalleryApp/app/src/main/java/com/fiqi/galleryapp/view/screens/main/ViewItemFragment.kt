package com.fiqi.galleryapp.view.screens.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.fiqi.galleryapp.R
import com.fiqi.galleryapp.databinding.FragmentViewItemBinding
import com.fiqi.galleryapp.view.dialog.confirmation.ConfirmationDialog
import com.fiqi.galleryapp.view.dialog.confirmation.ConfirmationDialogParam
import com.fiqi.galleryapp.view.viewmodels.main.MainViewModel
import com.squareup.picasso.Picasso

class ViewItemFragment : Fragment() {

  private var _binding: FragmentViewItemBinding? = null
  private val binding
    get() = _binding!!

  private val args: ViewItemFragmentArgs by navArgs()

  private val viewModel: MainViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentViewItemBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    if (args.imagemodel != null) {
      Picasso.get()
        .load(args.imagemodel?.link)
        .centerInside()
        .fit()
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_image_24)
        .into(binding.viewItemIvImage)
      binding.viewItemTvTitle.text = args.imagemodel?.title
      binding.viewItemTvDesc.text = args.imagemodel?.desc

      viewModel.setImageData(args.imagemodel!!)
    }
    setHasOptionsMenu(true)

    viewModel.getFailureMessage().observe(viewLifecycleOwner) { showToast(it) }

    viewModel.getSucceededMessage().observe(viewLifecycleOwner) {
      requireActivity().onBackPressed()
      showToast(it)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_main, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_action_delete -> {
        ConfirmationDialog(
          ConfirmationDialogParam(
            title = "Hapus Gambar",
            message = "Apakah Anda yakin ingin menghapus gambar ini?",
            onPositiveClick = {
              viewModel.deleteImagesData(viewModel.getImage().value?.id!!)
              requireActivity().onBackPressed()
            }
          )
        ).show(parentFragmentManager, null)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
  }
}
