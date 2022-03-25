package com.example.justforpractice.tasks.list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.example.justforpractice.R
import com.example.justforpractice.adapter.TaskAdapter
import com.example.justforpractice.base.BaseFragment
import com.example.justforpractice.databinding.ChooseDateModalBinding
import com.example.justforpractice.databinding.FragmentTaskListBinding
import com.example.justforpractice.events.AddTaskEvent
import com.example.justforpractice.model.Task
import com.example.justforpractice.utils.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.greenrobot.eventbus.Subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.OffsetTime

class TaskListFragment : BaseFragment(R.layout.fragment_task_list) {

    private var _binding: FragmentTaskListBinding? = null;
    private val binding get() = _binding!!

    private val viewModel by viewModel<TaskListViewModel>()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        registerForEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterFromEvents()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()

    }

    private fun setupObserver() {
        viewModel.getTasks().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data.isNullOrEmpty()) {
                        binding.tasksRv.visibility = View.GONE
                        binding.tasksEmptyView.visibility = View.VISIBLE
                    } else {
                        binding.tasksEmptyView.visibility = View.GONE
                        binding.tasksRv.visibility = View.VISIBLE
                    }
                    binding.tasksProgressBar.visibility = View.GONE
                    it.data?.let {
                        tasks -> adapter.taskList = tasks as ArrayList<Task>
                    }
                }
                Status.LOADING -> {
                    binding.tasksProgressBar.visibility = View.VISIBLE
                    binding.tasksRv.visibility = View.GONE
                    binding.tasksEmptyView.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.tasksProgressBar.visibility = View.GONE
                    toast(it?.message)
                }
            }
        }
    }

    private fun setupUI() {
        adapter = TaskAdapter(object: OnTaskClickListener {
            override fun onDoneButtonClick(task: Task) {
                TODO("Not yet implemented")
            }

            override fun onChipClick(task: Task) {
                showDatePicker(task)
            }

            override fun onTaskClick(task: Task) {
                TODO("Not yet implemented")
            }

        })
        binding.tasksRv.adapter = adapter
    }

    private fun showDatePicker(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ChooseDateModalBinding.inflate(inflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.show()
        dialog.setOnCancelListener {
            viewModel.deleteDateTime()
        }
        setDate(binding.addTaskCalendarView, task)
        task.time?.let {
            viewModel.setTime(it)
            setTimeText(binding.addTaskTimeEt, it.hour, it.minute)
        }
        setupTimeObserver(binding.addTaskTimeEt)
        binding.addTaskDateCancelButton.setOnClickListener { dialog.dismiss() }
        binding.addTaskDateDoneButton.setOnClickListener {
            viewModel.editDateTime(task)
            dialog.dismiss()
        }
        binding.addTaskCalendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.setDate(year, month, dayOfMonth)
        }
        binding.addTaskTimeEt.setOnClickListener {
            showTimePicker(task)
        }

    }

    private fun setDate(calendarView: CalendarView, task: Task) {
        viewModel.getDateFromTask(task)?.let {
            calendarView.date = it
        }
        viewModel.setDate(task.date)
    }

    private fun setupTimeObserver(addTaskTimeEt: TextInputEditText) {
        viewModel.timeToAdd.observe(viewLifecycleOwner) {
            it?.let {
                setTimeText(addTaskTimeEt, it.hour, it.minute)
            }
        }
    }

    private fun setTimeText(addTaskTimeEt: TextInputEditText, hour: Int, minute: Int) {
        addTaskTimeEt.setText("$hour:$minute")
    }

    private fun showTimePicker(task: Task) {
        task.time?.let { viewModel.setTime(it) }
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(task.time?.hour ?: OffsetTime.now().hour)
            .setMinute(task.time?.minute ?: OffsetTime.now().minute)
            .build()
        picker.show(parentFragmentManager, null)
        picker.addOnPositiveButtonClickListener {
            viewModel.setTime(OffsetTime.of(picker.hour, picker.minute, 0, 0, OffsetDateTime.now().offset))
            picker.dismiss()
        }
    }

    @Subscribe
    fun onAddTaskEvent(event: AddTaskEvent) {
//        viewModel.fetchTasks()
    }
}