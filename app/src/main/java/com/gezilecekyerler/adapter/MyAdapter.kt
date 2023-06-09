package com.gezilecekyerler.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gezilecekyerler.R
import com.gezilecekyerler.model.City

class MyAdapter(private val context: Activity, private val arrayList: List<City>):
    ArrayAdapter<City>(context,
    R.layout.list_item,arrayList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view: View =inflater.inflate(R.layout.list_item,null)
        val cid: Int
        val title: TextView = view.findViewById(R.id.txtTitle)


        title.text=arrayList[position].title.toString()


        mutableListOf<City>()
        return view
    }

}