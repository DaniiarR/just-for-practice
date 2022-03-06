package com.example.justforpractice.tasks.add

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.justforpractice.R
import com.example.justforpractice.databinding.ChooseDateModalBinding
import com.example.justforpractice.databinding.FragmentAddTaskModalBottomSheetBinding
import com.example.justforpractice.tasks.list.TaskListViewModel
import com.example.justforpractice.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset

class AddTaskModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTaskModalBottomSheetBinding? = null;
    private val binding get() = _binding!!

    private val viewModel by viewModel<TaskListViewModel>()

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
        binding.chooseDateButton.setOnClickListener {
            showDatePicker()
        }
        viewModel.createTask()
    }

    private fun showDatePicker() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ChooseDateModalBinding.inflate(inflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.show()
        viewModel.setDate(OffsetDateTime.now())
        binding.addTaskDateCancelButton.setOnClickListener { dialog.dismiss() }
        binding.addTaskDateDoneButton.setOnClickListener {
            //TODO Somehow save the date
        }
        binding.addTaskCalendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.setDate(OffsetDateTime.of(dayOfMonth, month, dayOfMonth, -1, -1, -1, -1, OffsetDateTime.now().offset))
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