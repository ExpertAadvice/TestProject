package com.roomdb.testproject.ui.fragments.broadcastreceivers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentBroadcastBinding
import com.roomdb.testproject.databinding.FragmentServicesBinding

class BroadcastFragment : Fragment() {

    private lateinit var binding: FragmentBroadcastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_broadcast, container, false)
        binding = FragmentBroadcastBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}