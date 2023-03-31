package com.roomdb.testproject.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentProfileBinding
import com.roomdb.testproject.ui.fragments.main.FragmentMainViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.passManager.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_fragmentMain)
        }

        binding.services.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_servicesFragment)
        }

        binding.broadcast.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_broadcastFragment)
        }

        binding.permission.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_permissionFragment)
        }
    }
}