package com.roman.batsu.ui.dialogfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.batsu.R
import com.roman.batsu.databinding.DialogRingsBinding

class RingsDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogRingsBinding
    val ringArgsText: String = "ringArgsText"
    val ringArgsDesc: String = "ringArgsDesc"
    val lottieFileName: String = "lottieFileName"

    private var ringsText: String? = null
    private var ringsDescription: String? = null


    companion object {
        @JvmStatic
        fun newInstance(ringsText: String,
                        ringsDescription: String,
                        lottieFile: Int) =
                RingsDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ringArgsText, ringsText)
                        putString(ringArgsDesc, ringsDescription)
                        putInt(lottieFileName, lottieFile)
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

        binding.textDescription.text = "$ringsText\n$ringsDescription"

        val lottieName: Int? = arguments?.getInt(lottieFileName, 0)
        val lottieAnimation: Int = getLottieAnimationFile(lottieName)
        binding.lottie.setAnimation(lottieAnimation)
        binding.lottie.playAnimation()

        return binding.root
    }


    /*
    * Возвращает название анимации лотти из RAW ресурсов
    * */
    private fun getLottieAnimationFile(lottieName: Int?): Int {
        return when (lottieName) {
            1 -> { R.raw.lottie_dashboard_analytics_two
            }
            else -> { R.raw.lottie_pizza
            }
        }
    }

}

