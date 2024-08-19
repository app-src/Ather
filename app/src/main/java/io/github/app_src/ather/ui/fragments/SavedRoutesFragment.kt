package io.github.app_src.ather.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import io.github.app_src.ather.R
import io.github.app_src.ather.databinding.FragmentSavedRoutesBinding
import io.github.app_src.ather.databinding.ItemSidebarSubMenuBinding
import io.github.app_src.ather.ui.fragments.SideBarFragment.OnSideBarMainMenuItemSelectedListener
import io.github.app_src.ather.utils.SideBarMainMenuItem

class SavedRoutesFragment : Fragment() {

    private var _binding: FragmentSavedRoutesBinding? = null
    private val binding get() = _binding!!
    private var listener: OnSideBarSubMenuItemSelectedListener? = null

    interface OnSideBarSubMenuItemSelectedListener {
        fun OnSideBarSubMenuItemSelected(item: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedRoutesBinding.inflate(inflater, container, false)

        // Define data
        val data = listOf(
            "Favorites" to listOf("House", "Hostel Parking", "Go Native"),
            "Frequents" to listOf("Ashirwad Supermarket", "Seven Eleven", "Cafe Coffee Day"),
            "Chargers" to listOf("Mantri Skyvilla", "Ather Supercharger", "New Friends Colony", "APJ Apartments")
        )

        // Set up the adapter
        val adapter = CustomAdapter(requireContext(), data,listener)
        binding.listView.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Custom Adapter for the ListView
    private class CustomAdapter(
        context: Context,
        private val data: List<Pair<String, List<String>>>,
        private val listener: OnSideBarSubMenuItemSelectedListener?,
    ) : ArrayAdapter<Pair<String, List<String>>>(context, 0, data) {

        private var selectedSideBarSubMenuItem: String = ""

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding = if (convertView == null) {
                ItemSidebarSubMenuBinding.inflate(LayoutInflater.from(context), parent, false)
            } else {
                ItemSidebarSubMenuBinding.bind(convertView)
            }

            val item = getItem(position)
            binding.itemTitle.text = item?.first

            // Clear previous buttons before adding new ones
            binding.buttonContainer.removeAllViews()

            // Add buttons dynamically
            item?.second?.forEach { label ->
                val button = TextView(context).apply {
                    text = label

                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setPadding(36, 18, 36, 18)
                        setMargins(8, 8, 8, 8)
                    }
                    if (selectedSideBarSubMenuItem == label) {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_corner_background, null)
                        setTextColor(resources.getColor(R.color.dark, null))
                    }else {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_corner_grey_background, null)
                        setTextColor(resources.getColor(R.color.white, null))
                    }
                    setOnClickListener{
                        selectedSideBarSubMenuItem = label
                        listener!!.OnSideBarSubMenuItemSelected(label)
                        notifyDataSetChanged()
                    }
                }
                binding.buttonContainer.addView(button)
            }

            return binding.root
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSideBarSubMenuItemSelectedListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnItemSelectedListener")
        }
    }
}