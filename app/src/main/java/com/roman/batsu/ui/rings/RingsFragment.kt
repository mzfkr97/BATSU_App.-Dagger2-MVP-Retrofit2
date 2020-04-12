package com.roman.batsu.ui.rings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.databinding.FragmentNotificationsBinding
import com.roman.batsu.ui.model.Rings
import com.roman.batsu.utils.JSONReader
import java.util.*

class RingsFragment : Fragment() {

    private var notificationArrayList: MutableList<Rings> = ArrayList()
    private val jsonReader = JSONReader()
    private lateinit var notificationsBinding: FragmentNotificationsBinding

    companion object {
        fun newInstance(): RingsFragment {
            return RingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)

        notificationsBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            notificationArrayList.clear()
            notificationsBinding.itemFooter.descriptionHeader.text = getString(R.string.description_header_notify)
            val inputStream = resources.openRawResource(R.raw.ring_lessons)
            notificationArrayList = jsonReader.createListJson(inputStream)

            adapter = RingsAdapter(context, notificationArrayList)
            notificationsBinding.recyclerView.adapter = adapter
        }
        return notificationsBinding.root
    }


}