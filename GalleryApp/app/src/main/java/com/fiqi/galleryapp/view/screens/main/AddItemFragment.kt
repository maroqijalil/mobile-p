package com.fiqi.galleryapp.view.screens.main

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

  private val viewModel: MainViewModel by activityViewModels()

  private lateinit var pickImageLauncher: ActivityResultLauncher<String>
  private lateinit var takeImageLauncher: ActivityResultLauncher<Uri>
  private lateinit var requestPremissionsLauncher: ActivityResultLauncher<Array<String>>

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentAddItemBinding.inflate(inflater, container, false)

    requestPremissionsLauncher =
      registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          permissions.forEach { (_, granted) ->
            if (!granted) {
              findNavController().navigateUp()
            }
          }
        }
      }
    pickImageLauncher =
      registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
          binding.addItemIvImage.setPadding(0)
          binding.addItemIvImage.setImageURI(uri)

          viewModel.setImageUriData(uri)
        }
      }
    takeImageLauncher =
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

    viewModel.setDatestampData(Date().time.toString())

    viewModel.getFailureMessage().observe(viewLifecycleOwner) {
      if (it != null) {
        showToast(it)
        viewModel.setFailureMessage(null)
      }
    }

    viewModel.getSucceededMessage().observe(viewLifecycleOwner) {
      if (it != null) {
        findNavController().navigateUp()
        showToast(it)
        viewModel.setSucceededMessage(null)
      }
    }

    setupButtons()
  }

  override fun onStart() {
    super.onStart()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val permissions = mutableListOf<String>()
      if (
        requireActivity().checkSelfPermission(Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_DENIED
      ) {
        permissions.add(Manifest.permission.CAMERA)
      }
      if (
        requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_DENIED
      ) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
      }
      if (
        requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_DENIED
      ) {
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
      }

      requestPremissionsLauncher.launch(permissions.toTypedArray())
    }
  }

  private fun setupButtons() {
    binding.addItemBtnCancel.setOnClickListener { findNavController().navigateUp() }

    binding.addItemBtnSave.setOnClickListener {
      if (validateInput()) {
        viewModel.insertImagesData(
          title = binding.addItemTilTitle.editText?.text.toString(),
          imageUri = viewModel.getImageUri().value!!,
          imageFormat = if (viewModel.getImageMimeType().value != null) {
            viewModel.getImageMimeType().value!!
          } else {
            MimeTypeMap.getSingleton()
              .getExtensionFromMimeType(
                requireActivity().contentResolver?.getType(viewModel.getImageUri().value!!)
              )!!
          },
          desc = binding.addItemTilDesc.editText?.text.toString()
        )
      }
    }

    binding.addItemIvImage.setOnClickListener {
      ImageChooserDialog(
        ImageChooserDialogParam(
          onPickImageClick = { pickImageLauncher.launch("image/*") },
          onTakeImageClick = {
            val file = File.createTempFile(
              viewModel.getDatestamp().value!!,
              ".jpg",
              requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )
            viewModel.setImageUriData(Uri.fromFile(file))
            viewModel.setImageMimeTypeData(".jpg")

            takeImageLauncher.launch(
              FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.provider",
                file
              )
            )
          }
        )
      ).show(parentFragmentManager, null)
    }
  }

  private fun validateInput(): Boolean {
    var isValidate = true

    binding.addItemIvErrImage.visibility = View.GONE

    if (binding.addItemTilTitle.editText?.text.isNullOrEmpty()) {
      binding.addItemTilTitle.editText?.error = "Isian tidak boleh kosong"
      isValidate = false
    }
    if (binding.addItemTilDesc.editText?.text.isNullOrEmpty()) {
      binding.addItemTilDesc.editText?.error = "Isian tidak boleh kosong"
      isValidate = false
    }
    if (viewModel.getImageUri().value == null) {
      binding.addItemIvErrImage.visibility = View.VISIBLE
      binding.addItemIvErrImage.error = "Pilih gambar terlebih dahulu"
      isValidate = false
    }

    return isValidate
  }

  private fun showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
