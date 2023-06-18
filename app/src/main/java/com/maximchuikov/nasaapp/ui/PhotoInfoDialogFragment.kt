package com.maximchuikov.nasaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.maximchuikov.nasaapp.R
import com.maximchuikov.nasaapp.data.model.Photo
import com.maximchuikov.nasaapp.databinding.FragmentPhotoInfoDialogBinding
import com.maximchuikov.nasaapp.util.loadUrl

class PhotoInfoDialogFragment : Fragment() {
    private lateinit var binding: FragmentPhotoInfoDialogBinding
    private lateinit var photo: Photo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoInfoDialogBinding.inflate(inflater, container, false)
        photo = arguments?.getParcelable<Photo>("filteredPhoto")!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photo.imgSrc.let { binding.ivPhoto.loadUrl(it) }
        binding.txtDate.text = "Date: ${photo.earthDate}"
        binding.txtRoverName.text = "Name: ${photo.rover.name}"
        binding.txtCamName.text = "Camera: ${photo.camera.name}"
        binding.txtStatus.text = "Status: ${photo.rover.status}"
        binding.txtLaunchDate.text = "Launch Date: ${photo.rover.launchDate}"
        binding.txtLandingDate.text = "Landing Date: ${photo.rover.landingDate}"
    }
}
