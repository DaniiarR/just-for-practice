package com.example.justforpractice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justforpractice.OnItemClickListener
import com.example.justforpractice.model.Task
import com.example.justforpractice.databinding.TaskListItemBinding

class TaskAdapter(val clickListener: OnItemClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = arrayListOf<Task>()

    inner class TaskViewHolder(val binding: TaskListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, clickListener: OnItemClickListener) {
            binding.taskRb.text = task.name
            binding.taskRb.isChecked = task.isDone
        }
    }

    fun setValues(tasks: ArrayList<Task>) {
        taskList = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}