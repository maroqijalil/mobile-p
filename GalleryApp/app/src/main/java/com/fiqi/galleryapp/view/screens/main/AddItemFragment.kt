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
  private lateinit var requestPremissionsLauncher: ActivityResultLauncher<Array<String>>

  companion object {
    const val PERMISSION_REQUEST_GALLERY = 901
    const val PERMISSION_REQUEST_CAMERA = 801
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentAddItemBinding.inflate(inflater, container, false)

    requestPremissionsLauncher =
      registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          permissions.forEach { (permit, granted) ->
            when (permit) {
              Manifest.permission.READ_EXTERNAL_STORAGE -> {
                if (granted) {
                  val action = viewModel.getReadExternalStorageAction().value
                  if (action != null) {
                    action()
                  }
                } else {
                  showToast("Akses penyimpanan ditolak")
                }
              }
              Manifest.permission.CAMERA -> {
                if (granted) {
                  val action = viewModel.getCameraAction().value
                  if (action != null) {
                    action()
                  }
                } else {
                  showToast("Akses kamera ditolak")
                }
              }
            }
          }
        }
      }
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
      if (validateInput()) {
        viewModel.addImageData(
          title = binding.addItemTilTitle.editText?.text.toString(),
          imageUri = viewModel.getImageUri().value!!,
          imageFormat = MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity?.contentResolver?.getType(viewModel.getImageUri().value!!))!!,
          desc = binding.addItemTilDesc.editText?.text.toString()
        )
      }
    }

    binding.addItemIvImage.setOnClickListener {
      ImageChooserDialog(
        ImageChooserDialogParam(
          onPickImageClick = {
            readExternalStoragePermissionFor {
              pickImageContract.launch("image/*")
            }
          },
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

            cameraPermissionFor { takeImageContract.launch(viewModel.getImageUri().value) }
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

  private fun readExternalStoragePermissionFor(action: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val permission = Manifest.permission.READ_EXTERNAL_STORAGE
      if (activity?.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
        viewModel.setReadExternalStorageAction(action)
        requestPremissionsLauncher.launch(arrayOf(permission))
      } else {
        action()
      }
    } else {
      action()
    }
  }

  private fun cameraPermissionFor(action: () -> Unit = {}) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val permission = Manifest.permission.CAMERA
      if (activity?.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
        viewModel.setCameraAction(action)
        requestPremissionsLauncher.launch(arrayOf(permission))
      } else {
        action()
      }
    } else {
      action()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun showToast(msg: String) {
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_LONG).show()
  }
}
