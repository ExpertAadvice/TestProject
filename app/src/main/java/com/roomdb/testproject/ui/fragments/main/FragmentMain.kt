package com.roomdb.testproject.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentMainBinding
import com.roomdb.testproject.domain.model.PassManager
import com.roomdb.testproject.ui.adapter.PassRecyclerViewAdapter
import com.roomdb.testproject.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentMain : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: FragmentMainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.passList.collectLatest {
                setRecyclerViewData(it)
            }
        }
        lifecycleScope.launch {
            viewModel.showToast.collectLatest {
                val msg = when (it) {
                    is EventType.Delete -> "${it.size} Entry Deleted"
                    is EventType.Update -> "${it.size} Entry Updated"
                    is EventType.Insert -> if (it.bool) "Entry Inserted" else "Please Provide Unique key value"
                }
                requireContext().toast(msg)
                if (msg.length<20) {
                    clearTextViews()
                }
            }
        }

        clickListeners()
    }


    private fun clickListeners() {
        binding.showAllButton.setOnClickListener {
            viewModel.getPasswordList()
        }

        binding.addButton.setOnClickListener {
            viewModel.addPass(readTvValues())
        }

        binding.clearButton.setOnClickListener {
            clearTextViews()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deletePassUsingKey(readTvValues())
        }

        binding.passwordManagerTitle.setOnClickListener {
            viewModel.updatePass(readTvValues())
        }
    }

    private fun clearTextViews() {
        binding.passKey.text = null
        binding.passValue.text = null
        binding.passDesc.text = null
    }

    private fun readTvValues(): PassManager {
        return PassManager(
            passKey = binding.passKey.text.toString(),
            passValue = binding.passValue.text.toString(),
            desc = binding.passDesc.text.toString()
        )
    }

    private fun setRecyclerViewData(list: List<PassManager>?) {
        val adapter = PassRecyclerViewAdapter(list)
        adapter.setItemClickListener(object : PassRecyclerViewAdapter.OnClick {
            override fun onRecordClick(position: Int, passManager: PassManager?) {
                binding.passKey.setText(passManager!!.passKey)
                binding.passValue.setText(passManager.passValue)
                binding.passDesc.setText(passManager.desc ?: "")
            }
        })
        binding.passRecyclerview.apply {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }
}