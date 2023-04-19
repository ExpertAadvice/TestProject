package com.roomdb.testproject.ui.fragments.services

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentServicesBinding
import com.roomdb.testproject.ui.fragments.services.channelclass.ServiceExample
import com.roomdb.testproject.utils.toast

class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_services, container, false)
        binding = FragmentServicesBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        if (!checkNotificationPermission()) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.launchNotification.setOnClickListener {
            val intent = Intent(requireContext(), ServiceExample::class.java)
            ContextCompat.startForegroundService(requireContext(), intent)
        }
    }

    private fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notification = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            )
            notification == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            requireContext().toast("Notification Permission Granted")
        } else {
            requireContext().toast("Notification Permission Denied")
        }
    }

}