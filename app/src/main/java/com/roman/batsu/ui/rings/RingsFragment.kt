package com.roman.batsu.ui.rings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.roman.batsu.R
import com.roman.batsu.databinding.FragmentNotificationsBinding
import com.roman.batsu.ui.dialogfragment.RingsDialogFragment
import com.roman.batsu.ui.model.Rings
import com.roman.batsu.utils.JSONReader
import java.util.*
import kotlin.math.log

class RingsFragment : Fragment(), RingsAdapter.RingsItemClick {

    private var notificationArrayList: MutableList<Rings> = ArrayList()
    private val jsonReader = JSONReader()
    private lateinit var notificationsBinding: FragmentNotificationsBinding
    private lateinit var adapter:RingsAdapter

    companion object {
        fun newInstance(): RingsFragment {
            return RingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsBinding =
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.fragment_notifications,
                        container,
                        false)
        adapter = activity?.let { RingsAdapter(it, notificationArrayList) }!!
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

        adapter.setAutoOnItemClickListener(this)
        notificationsBinding.itemFooter.cardFooter.setOnClickListener(){
            showDialog("", getString(R.string.description_header_notify), 2)

        }

        return notificationsBinding.root
    }

    override fun onClick(item: Rings?) {
        val title: String? = item?.title
        val description: String? = item?.description
        showDialog(title.toString(),description.toString(), 1)


    }

    private fun showDialog(ringsText: String, ringsDescription: String, lottieFileName: Int){

        val onBoardingDialog =
                RingsDialogFragment.newInstance(
                        ringsText,
                        ringsDescription,
                        lottieFileName
        )

        onBoardingDialog.isCancelable = true
        activity?.supportFragmentManager?.let {
            onBoardingDialog.show(it, "onBoardingDialogFragment")
        }

    }


}