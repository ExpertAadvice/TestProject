package com.roomdb.testproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.PassRecyclerRowBinding
import com.roomdb.testproject.domain.model.PassManager

class PassRecyclerViewAdapter(list2: List<PassManager>?) :
    RecyclerView.Adapter<PassRecyclerViewAdapter.ViewHolder>() {

    private val list = list2
    private lateinit var binding: PassRecyclerRowBinding
    private var listener: OnClick? = null

    interface OnClick {
        fun onRecordClick(position: Int, passManager: PassManager?)
    }

    fun setItemClickListener(listener2: OnClick) {
        listener = listener2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pass_recycler_row, parent, false)
        binding = PassRecyclerRowBinding.bind(view)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.passKeyTv.text = list?.get(position)?.passKey
        binding.passValueTv.text = list?.get(position)?.passValue
        binding.passDescTv.text = list?.get(position)?.desc

        holder.itemView.setOnClickListener {
            listener?.onRecordClick(position, list?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class ViewHolder(binding: PassRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)
}