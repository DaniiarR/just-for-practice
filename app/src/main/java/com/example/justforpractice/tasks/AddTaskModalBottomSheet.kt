package com.example.justforpractice.tasks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.justforpractice.R
import com.example.justforpractice.databinding.FragmentAddTaskModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*

class AddTaskModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTaskModalBottomSheetBinding? = null;
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskModalBottomSheetBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            showKeyboard(binding.addTaskEt)
        }
    }

    private suspend fun showKeyboard(view: View) {
        delay(50)
        view.requestFocus()
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            view, InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
}