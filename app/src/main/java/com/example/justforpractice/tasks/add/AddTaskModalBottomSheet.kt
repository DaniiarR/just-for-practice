package com.example.justforpractice.tasks.add

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.justforpractice.R
import com.example.justforpractice.databinding.ChooseDateModalBinding
import com.example.justforpractice.databinding.FragmentAddTaskModalBottomSheetBinding
import com.example.justforpractice.tasks.list.TaskListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.*

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
        viewModel.createTask()

        binding.chooseDateButton.setOnClickListener {
            showDatePicker()
            setupDateObserver(binding.dateTimeChip)
            setupTimeObserver(binding.dateTimeChip)
            binding.dateTimeChip.setOnCloseIconClickListener { viewModel.deleteDateTime() }
        }
        binding.addDescriptionButton.setOnClickListener {
            binding.addDescriptionTiL.visibility = View.VISIBLE
            binding.addDescriptionEt.requestFocus()
        }
        binding.addTaskEt.doOnTextChanged { text, _, _, _ ->
            viewModel.setTaskName(text.toString())
        }
        binding.addDescriptionEt.doOnTextChanged { text, _, _, _ ->
            viewModel.setTaskDescription(text.toString())
        }
    }

    private fun setupDateObserver(chip: Chip) {
        viewModel.dateToAdd.observe(viewLifecycleOwner) {
            if (it != null){
                viewModel.addDateToTask(it)
                chip.visibility = View.VISIBLE
                chip.text = viewModel.getFormattedDateTime()
            } else {
                chip.visibility = View.GONE
            }
        }
    }

    private fun setupTimeObserver(chip: Chip) {
        viewModel.timeToAdd.observe(viewLifecycleOwner) {
            it?.let { chip.text = viewModel.getFormattedDateTime() }
        }
    }

    private fun setupTimeObserver(addTaskTimeEt: TextInputEditText) {
        viewModel.timeToAdd.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.addTimeToTask(it)
                setTimeText(addTaskTimeEt, it.hour, it.minute)
            }
        }
    }

    private fun setTimeText(addTaskTimeEt: TextInputEditText, hour: Int, minute: Int) {
         addTaskTimeEt.setText("$hour:$minute")
    }

    private fun showDatePicker() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ChooseDateModalBinding.inflate(inflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.show()
        setDate(binding.addTaskCalendarView)
        setupTimeObserver(binding.addTaskTimeEt)
        binding.addTaskDateCancelButton.setOnClickListener { dialog.dismiss() }
        binding.addTaskDateDoneButton.setOnClickListener { dialog.dismiss() }
        binding.addTaskCalendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.setDate(year, month, dayOfMonth)
        }
        binding.addTaskTimeEt.setOnClickListener {
            showTimePicker()
        }

    }

    private fun setDate(calendarView: CalendarView) {
        viewModel.getDate()?.let {
            calendarView.date = it
            return
        }
        viewModel.setDate(LocalDate.now())
    }

    private fun showTimePicker() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(viewModel.getHours() ?: OffsetTime.now().hour)
                .setMinute(viewModel.getMinutes() ?: OffsetTime.now().minute)
                .build()
        picker.show(parentFragmentManager, null)
        picker.addOnPositiveButtonClickListener {
            viewModel.setTime(OffsetTime.of(picker.hour, picker.minute, 0, 0, OffsetDateTime.now().offset))
            picker.dismiss()
        }
    }

    private suspend fun showKeyboard(view: View) {
        delay(50)
        view.requestFocus()
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            view, InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.deleteNewTask()
        viewModel.deleteDateTime()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
}