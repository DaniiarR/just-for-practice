package com.example.justforpractice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justforpractice.model.Task
import com.example.justforpractice.databinding.TaskListItemBinding
import com.example.justforpractice.utils.OnTaskClickListener
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class TaskAdapter(val clickListener: OnTaskClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var taskList = arrayListOf<Task>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(val binding: TaskListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, clickListener: OnTaskClickListener) {
            binding.taskRb.isChecked = task.isDone
            binding.taskRb.setOnCheckedChangeListener { buttonView, isChecked ->
                clickListener.onChipClick(task)
            }
            binding.dateTimeChip.setOnClickListener {
                clickListener.onChipClick(task)
            }
            binding.task.setOnClickListener {
                clickListener.onTaskClick(task)
            }
            task.name?.let { binding.taskNameTv.text = it }
            task.additionalInfo?.let { binding.taskDescriptionTv.text = it }
            task.date?.let {
                binding.dateTimeChip.text = getFormattedDateTime(it, task.time)
                binding.dateTimeChip.visibility = View.VISIBLE
            }
        }

        private fun getFormattedDateTime(date: LocalDate, time: org.threeten.bp.OffsetTime?): String {
            val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd")
            var dateTime = formatter.format(date)
            time?.let {
                dateTime += ", " + DateTimeFormatter.ofPattern("HH:mm").format(it)
            }
            return dateTime
        }
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