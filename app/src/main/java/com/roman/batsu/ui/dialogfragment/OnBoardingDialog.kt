package com.roman.batsu.ui.dialogfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.batsu.R
import com.roman.batsu.databinding.DialogOnboardingBinding
import com.roman.batsu.utils.TextUtils

class OnBoardingDialog : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: DialogOnboardingBinding

    companion object {
        private const val PREFS_NAME = "firstStartRun"
        fun newInstance(): OnBoardingDialog {
            return OnBoardingDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.dialog_onboarding, container, false)

        preferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        binding.textTitle.text = getString(R.string.on_boarding_dialog_title)
        TextUtils.setTextWithLinks(binding.textDescription,
                TextUtils.fromHtml(getString(R.string.on_boarding_dialog_description)))
        binding.lottie.playAnimation()
        binding.letsGoButton.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View) {
        preferences.edit { putBoolean("isFirstRun", false) }
        this.dismiss()
    }


}