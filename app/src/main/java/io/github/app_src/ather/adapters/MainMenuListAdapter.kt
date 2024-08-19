package io.github.app_src.ather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import io.github.app_src.ather.R
import io.github.app_src.ather.utils.SideBarMainMenuItem

class MainMenuListAdapter (context: Context, private val items: List<SideBarMainMenuItem>) :
    ArrayAdapter<SideBarMainMenuItem>(context, 0, items) {

        private var selectedSideBarMainMenuItem: Int = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        // Inflate the custom layout if it hasn't been inflated already
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_sidebar_main_menu, parent, false)

        // Find the ImageView in the custom layout
        val imageView = view.findViewById<ImageView>(R.id.itemIcon)

        // Populate the data into the template view using the data object
        item?.let {
            imageView.setImageResource(it.imageResId)
        }

        if (position == selectedSideBarMainMenuItem){
            view.setBackgroundColor(context.resources.getColor(R.color.white))
            view.setBackgroundResource(R.drawable.rounded_corner_background)
            imageView.setBackgroundColor(context.resources.getColor(R.color.white))
            imageView.drawable.setTint(context.resources.getColor(R.color.dark))
        } else {
            imageView.setBackgroundColor(context.resources.getColor(R.color.grey))
            imageView.drawable.setTint(context.resources.getColor(R.color.light_grey))
            view.setBackgroundColor(context.resources.getColor(R.color.transparent))
        }

        return view
    }

    fun setSelectedPosition(position: Int) {
        selectedSideBarMainMenuItem = position
        notifyDataSetChanged()
    }
}
