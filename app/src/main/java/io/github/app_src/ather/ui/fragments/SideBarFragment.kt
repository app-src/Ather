package io.github.app_src.ather.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.app_src.ather.adapters.MainMenuListAdapter
import io.github.app_src.ather.R
import io.github.app_src.ather.utils.SideBarMainMenuItem
import io.github.app_src.ather.databinding.FragmentSideBarBinding
import io.github.app_src.ather.ui.MainActivity


class SideBarFragment : Fragment() {

    private var listener: OnSideBarMainMenuItemSelectedListener? = null

    interface OnSideBarMainMenuItemSelectedListener {
        fun OnSideBarMainMenuItemSelected(item: SideBarMainMenuItem)
    }

    private lateinit var binding: FragmentSideBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sample data
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSideBarBinding.inflate(inflater, container, false)
        val items = listOf(
            SideBarMainMenuItem(true,"Location", R.drawable.icon_location),
            SideBarMainMenuItem(false,"Bluetooth", R.drawable.icon_bluetooth),
            SideBarMainMenuItem(false,"Settings", R.drawable.icon_settings)
        )
        val adapter = MainMenuListAdapter(activity as MainActivity, items)
        binding.sideBarMainMenuListview?.adapter = adapter

        binding.sideBarMainMenuListview?.setOnItemClickListener { parent, view, position, id ->
            listener?.OnSideBarMainMenuItemSelected(items[position])
            adapter.setSelectedPosition(position)
        }

        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSideBarMainMenuItemSelectedListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnItemSelectedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}