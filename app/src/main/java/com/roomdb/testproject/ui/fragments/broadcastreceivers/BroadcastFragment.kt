package com.roomdb.testproject.ui.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_services, container, false)
        binding = FragmentServicesBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}