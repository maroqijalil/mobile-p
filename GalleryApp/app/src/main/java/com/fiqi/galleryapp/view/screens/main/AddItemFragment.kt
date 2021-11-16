package com.fiqi.galleryapp.view.screens.main

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.fiqi.galleryapp.BuildConfig
import com.fiqi.galleryapp.databinding.FragmentAddItemBinding
import com.fiqi.galleryapp.view.dialog.imagechooser.ImageChooserDialog
import com.fiqi.galleryapp.view.dialog.imagechooser.ImageChooserDialogParam
import com.fiqi.galleryapp.view.viewmodels.main.MainViewModel
import java.io.File
import java.util.*

class AddItemFragment : Fragment() {

  private var _binding: FragmentAddItemBinding? = null
  private val binding
    get() = _binding!!

  private val args: AddItemFragmentArgs by navArgs()

  private val viewModel: MainViewModel by activityViewModels()

  private lateinit var pickImageContract: ActivityResultLauncher<String>
  private lateinit var takeImageContract: ActivityResultLauncher<Uri>

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentAddItemBinding.inflate(inflater, container, false)

    pickImageContract =
      registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
          binding.addItemIvImage.setPadding(0)
          binding.addItemIvImage.setImageURI(uri)

          viewModel.setImageUriData(uri)
        }
      }
    takeImageContract =
      registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccesfull ->
        if (isSuccesfull) {
          binding.addItemIvImage.setPadding(0)
          binding.addItemIvImage.setImageURI(viewModel.getImageUri().value)
        }
      }

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    if (args.datestamp != null) {
      viewModel.setDatestampData(args.datestamp!!)
    } else {
      viewModel.setDatestampData(Date().time.toString())
    }

    setupButtons()
  }

  private fun setupButtons() {
    binding.addItemBtnCancel.setOnClickListener { requireActivity().onBackPressed() }

    binding.addItemBtnSave.setOnClickListener {
    }

    binding.addItemIvImage.setOnClickListener {
      ImageChooserDialog(
        ImageChooserDialogParam(
          onPickImageClick = { pickImageContract.launch("image/*") },
          onTakeImageClick = {
            val file = File(
              requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
              viewModel.getDatestamp().value + ".jpg"
            )
            viewModel.setImageUriData(
              FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.provider",
                file
              )
            )

            takeImageContract.launch(viewModel.getImageUri().value)
          }
        )
      ).show(parentFragmentManager, null)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
