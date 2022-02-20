package com.example.justforpractice.tasks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.justforpractice.OnItemClickListener
import com.example.justforpractice.R
import com.example.justforpractice.adapter.TaskAdapter
import com.example.justforpractice.adapter.ViewPagerAdapter
import com.example.justforpractice.base.BaseFragment
import com.example.justforpractice.databinding.FragmentTaskListBinding
import com.example.justforpractice.databinding.FragmentTaskListContainerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class TaskContainerFragment : BaseFragment(R.layout.fragment_task_list_container) {
    private var _binding: FragmentTaskListContainerBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListContainerBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        binding.addTaskFab.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val modalBottomSheet = AddTaskModalBottomSheet()
        modalBottomSheet.show(parentFragmentManager, null)
//        val modalBottomSheetBehavior = (modalBottomSheet.dialog as BottomSheetDialog).behavior
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Done"
                1 -> tab.text = "My List"
            }
        }.attach()
        binding.tabLayout.getTabAt(1)?.select()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
}