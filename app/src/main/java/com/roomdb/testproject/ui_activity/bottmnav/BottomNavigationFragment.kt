package com.roomdb.testproject.ui_activity.bottmnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentBottomNavigationBinding
import com.roomdb.testproject.databinding.FragmentBroadcastBinding
import com.roomdb.testproject.databinding.FragmentServicesBinding

class BottomNavigationFragment : Fragment() {

    private lateinit var binding: FragmentBottomNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false)
        binding = FragmentBottomNavigationBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv1.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavigationFragment_to_drawerLayoutFragment)
        }

    }

}