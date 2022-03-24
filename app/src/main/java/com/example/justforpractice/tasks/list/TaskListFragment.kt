package com.example.justforpractice.tasks.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.justforpractice.R
import com.example.justforpractice.adapter.TaskAdapter
import com.example.justforpractice.base.BaseFragment
import com.example.justforpractice.databinding.FragmentTaskListBinding
import com.example.justforpractice.events.AddTaskEvent
import com.example.justforpractice.model.Task
import com.example.justforpractice.utils.*
import org.greenrobot.eventbus.Subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        adapter = TaskAdapter { task ->

        }
        binding.tasksRv.adapter = adapter
    }

    @Subscribe
    fun onAddTaskEvent(event: AddTaskEvent) {
//        viewModel.fetchTasks()
    }
}