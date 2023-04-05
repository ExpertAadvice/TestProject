package com.roomdb.testproject.ui.fragments.permissionmanager

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentPermissionBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


open class PermissionFragment : Fragment() {

    private lateinit var binding: FragmentPermissionBinding
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_permission, container, false)
        binding = FragmentPermissionBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openCamera.setOnClickListener {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.openGallery.setOnClickListener {
            galleryPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            binding.cameraPermissionTv.text = getString(R.string.camera_permission_granted)
            imageUri = createImageFile()!!
            cameraIntentLauncher.launch(imageUri)
        } else {
            shouldShowRationaleInfo()
        }
    }

    private val cameraIntentLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        imageUri?.let { binding.imageViewCamera.setImageURI(it) }
    }

    private val galleryPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { binding.imageViewGallery.setImageURI(it) }
    }

    private fun shouldShowRationaleInfo() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Camera permission is Required")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
                binding.cameraPermissionTv.text = getString(R.string.camera_permission_denied)
            }

        val alert = dialogBuilder.create()
        alert.setTitle("AlertDialog")
        alert.show()
    }

    private fun createImageFile(): Uri? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File(requireContext().filesDir, "${imageFileName}.jpg")
        return FileProvider.getUriForFile(requireContext(),
            "com.roomdb.testproject.fileprovider", image
        )
    }
}