package com.example.justforpractice.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justforpractice.OnItemClickListener
import com.example.justforpractice.R
import com.example.justforpractice.adapter.TaskAdapter
import com.example.justforpractice.base.BaseFragment
import com.example.justforpractice.databinding.FragmentTaskListBinding
import com.example.justforpractice.model.Task

class TaskListFragment : BaseFragment(R.layout.fragment_task_list) {
    private var _binding: FragmentTaskListBinding? = null;
    private val binding get() = _binding!!

    private var adapter: TaskAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateTasks()
    }

    private fun populateTasks() {
        adapter = adapter ?: TaskAdapter(object : OnItemClickListener {
            override fun <T> onItemClick(listItem: T) {

            }
        })
        val tasks = arrayListOf<Task>()
        tasks.add(Task("buy groceries", null, false));
        tasks.add(Task("buy groceries", null, false));
        tasks.add(Task("buy groceries", null, false));
        tasks.add(Task("buy groceries", null, false));
        adapter?.setValues(tasks);
        binding.taskListRv.adapter = adapter;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
}