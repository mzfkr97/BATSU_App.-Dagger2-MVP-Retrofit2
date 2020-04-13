package com.roman.batsu.ui.dialogfragment

import android.annotation.SuppressLint
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.batsu.R
import com.roman.batsu.databinding.DialogRingsBinding
import com.roman.batsu.utils.TextUtils

class RingsDialogFragment : BottomSheetDialogFragment() , View.OnClickListener{

    private lateinit var binding: DialogRingsBinding
    val ringArgsText: String = "ringArgsText"
    val ringArgsDesc: String = "ringArgsDesc"
    private var ringsText: String? = null
    private var ringsDescription: String? = null

    companion object {
        @JvmStatic
        fun newInstance(ringsText: String, ringsDescription: String) =
                RingsDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ringArgsText, ringsText)
                        putString(ringArgsDesc, ringsDescription)
                    }
                }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.dialog_rings, container, false)

        arguments?.getString(ringArgsText)?.let { ringsText = it }
        arguments?.getString(ringArgsDesc)?.let { ringsDescription = it }

        binding.textDescription.text = ringsText + "\n" + ringsDescription
        binding.lottie.playAnimation()

//        binding.buttonAlarm.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
//        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
//        context?.startActivity(intent)
    }
}

