package com.example.contactapp.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.contactapp.databinding.FragmentViewContactBinding

class ViewContactFragment: Fragment() {

  private var binding: FragmentViewContactBinding? = null

  private val args: ViewContactFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentViewContactBinding.inflate(inflater, container, false)

    if (args.contact != null) {
      binding?.itemCTvName?.text = args.contact?.nama
      binding?.itemCTvNum?.text = args.contact?.telepon
    }

    binding?.viewFabCall?.setOnClickListener {
      val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + args.contact?.telepon))
      startActivity(intent)
    }

    return binding?.root
  }
}
