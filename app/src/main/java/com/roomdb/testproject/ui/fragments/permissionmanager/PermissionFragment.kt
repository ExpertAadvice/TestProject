package com.roomdb.testproject.ui.fragments.permissionmanager

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentPermissionBinding
import com.roomdb.testproject.ui.fragments.permissionmanager.locationutils.LocationService
import com.roomdb.testproject.utils.hasLocationPermission
import com.roomdb.testproject.utils.toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PermissionFragment : Fragment() {

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openCamera.setOnClickListener {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.openGallery.setOnClickListener {
            galleryPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            binding.galleryPermissionTv.text = getString(R.string.gallery_permission)
        }

        binding.getReadWritePermission.setOnClickListener {
            if (checkStoragePermissions()) {
                // Do Something here
                binding.externalStorageTv.text = getString(R.string.storage_permission)
            } else {
                binding.externalStorageTv.text = getString(R.string.storage_permission_denied)
                requestStoragePermissions()
            }
        }

        binding.latLngTv.text = "Location Permission Denied"

        binding.startLocation.setOnClickListener {
            if (requireContext().hasLocationPermission()) {

                binding.latLngTv.text = "Location Permission Granted"
                val intent = Intent(requireContext(), LocationService::class.java)
                intent.action = LocationService.ACTION_START
                ContextCompat.startForegroundService(requireContext(), intent)
            } else {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }

        binding.stopLocation.setOnClickListener {
            val intent = Intent(requireContext(), LocationService::class.java)
            intent.action = LocationService.ACTION_STOP
            ContextCompat.startForegroundService(requireContext(), intent)
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(
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
        imageUri.let { binding.imageViewCamera.setImageURI(it) }
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
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
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
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File(requireContext().filesDir, "${imageFileName}.jpg")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.roomdb.testproject.fileprovider", image
        )
    }


    private fun checkStoragePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val read = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val write = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                storagePermissionLauncher.launch(intent)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                storagePermissionLauncher.launch(intent)
            }
        } else {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            storagePermissionBelow11Launcher.launch(permissions)
        }
    }

    private val storagePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                // Do Something here
                requireContext().toast("External storage permission granted")
            } else {
                // Do Something here
                requireContext().toast("External storage permission denied")
            }
        } else {
            // Android is below 11 R
        }
    }

    private val storagePermissionBelow11Launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->

        var allPermissionGranted = true
        val deniedPermissionList = ArrayList<String>()

        for (result in resultMap) {
            deniedPermissionList.add(result.key)
            allPermissionGranted = allPermissionGranted && result.value
        }

        if (allPermissionGranted) {
            requireContext().toast("External storage permission granted")
            // Do Something here
        } else {
            requireContext().toast("External storage permission denied")
            // Handle cases
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->

        var allPermissionGranted = true
        val deniedPermissionList = ArrayList<String>()

        for (result in resultMap) {
            deniedPermissionList.add(result.key)
            allPermissionGranted = allPermissionGranted && result.value
        }

        if (allPermissionGranted) {
            requireContext().toast("Location permission granted")
            // Do Something here
        } else {
            requireContext().toast("Location permission denied")
            // Handle cases
        }
    }

}