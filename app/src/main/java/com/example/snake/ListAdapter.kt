package com.example.snake

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context : Context, textL : List<String>, icon : List<Int>) : BaseAdapter() {
    private var context: Context? = null
    private var textL: List<String>
    private var icon: List<Int>

    init{
        this.context = context
        this.textL = textL
        this.icon = icon
    }

    override fun getCount() : Int {
        return icon.size
    }

    override fun getItem(i : Int) : Any {
        return i
    }

    override fun getItemId(i : Int) : Long{
        return i.toLong()
    }

    override fun getView(position : Int, convertView : View?, parent : ViewGroup) : View? {
        val viewHolder : ViewHolder
        var convertV = convertView
        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertV = inflater.inflate(R.layout.point_list, parent, false)
            viewHolder.textL = convertV.findViewById(R.id.points) as TextView
            viewHolder.icon = convertV.findViewById(R.id.icon) as ImageView
            convertV?.tag = viewHolder
        } else {
            viewHolder = convertV?.tag as ViewHolder
        }
        viewHolder.textL?.text = textL[position]
        viewHolder.icon?.setImageResource(icon[position])
        return convertV
    }

    private class ViewHolder {
        var textL : TextView? = null
        var icon : ImageView? = null
    }
}