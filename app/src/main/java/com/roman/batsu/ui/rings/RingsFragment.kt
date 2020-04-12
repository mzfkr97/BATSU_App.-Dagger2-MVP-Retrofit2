package com.roman.batsu.ui.rings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.ui.model.Rings
import com.roman.batsu.utils.JSONReader
import java.util.*

class RingsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RingsAdapter
    private var notificationArrayList: MutableList<Rings> = ArrayList()
    private val jsonReader = JSONReader()

    companion object {
        fun newInstance(): RingsFragment {
            return RingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        setUpRecyclerView(view)
        notificationArrayList.clear()
        val descriptionHeader = view.findViewById<TextView>(R.id.descriptionHeader)
        descriptionHeader.text = getString(R.string.description_header_notify)
        val inputStream = resources.openRawResource(R.raw.ring_lessons)
        notificationArrayList = jsonReader.createListJson(inputStream)
        adapter = RingsAdapter(activity, notificationArrayList)
        recyclerView.adapter = adapter
        return view
    }

    private fun setUpRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
    }


}