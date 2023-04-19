package com.roomdb.testproject.ui_activity.uihome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentHomeUiBinding
import com.roomdb.testproject.databinding.UiRecyclerRowBinding

class HomeUiFragment : Fragment() {

    private lateinit var binding: FragmentHomeUiBinding
    private var uiAdapter: UiRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_home_ui, container, false)
        binding = FragmentHomeUiBinding.bind(view)

        uiAdapter = UiRvAdapter(OnRecyclerItemClick {
            when (it) {
                getString(R.string.bottom_navigation) -> {
                    findNavController().navigate(R.id.action_homeUiFragment_to_bottomNavigationFragment)
                }
                getString(R.string.bottomSheet) -> {
                    findNavController().navigate(R.id.action_homeUiFragment_to_bottomSheetFragment)
                }
                getString(R.string.tab_bar) -> {
                    findNavController().navigate(R.id.action_homeUiFragment_to_tabLayoutFragment)
                }
                getString(R.string.drawer_layout) -> {
                    findNavController().navigate(R.id.action_homeUiFragment_to_drawerLayoutFragment)
                }
            }
        })

        binding.uiRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = uiAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = mutableListOf<String>()
        list.add(getString(R.string.bottom_navigation))
        list.add(getString(R.string.drawer_layout))
        list.add(getString(R.string.tab_bar))
        list.add(getString(R.string.bottomSheet))

        // Setting data to recycler view adapter.
        uiAdapter?.itemList = list

    }
}

/**
 * Click listener for Videos. By giving the block a name it helps a reader understand what it does.
 *
 */
class OnRecyclerItemClick(val block: (String) -> Unit) {

    fun onClick(title: String) = block(title)
}


class UiRvAdapter(val callback: OnRecyclerItemClick) : RecyclerView.Adapter<UiViewHolder>() {

    private lateinit var rvBinding: UiRecyclerRowBinding
    var itemList: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UiViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(UiViewHolder.LAYOUT, parent, false)
        rvBinding = UiRecyclerRowBinding.bind(view)

        return UiViewHolder(rvBinding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: UiViewHolder, position: Int) {

        holder.rvBinding.also {
            it.titleString = itemList[position]
            it.callback = callback
        }
    }

}

class UiViewHolder(val rvBinding: UiRecyclerRowBinding) : RecyclerView.ViewHolder(rvBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.ui_recycler_row
    }
}