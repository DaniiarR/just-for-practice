package com.example.justforpractice.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes contentView: Int) : Fragment(contentView) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.backStackEntryCount >= 1) {
                    parentFragmentManager.popBackStack()
                } else {
                    activity?.finish()
                }
            }
        })
    }

}