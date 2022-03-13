package com.example.justforpractice.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

interface OnItemClickListener {
    fun <T> onItemClick(listItem: T)
}

fun Fragment.toast(message: String?) {
    Toast.makeText(this.requireContext(), message ?: "An error occurred.", Toast.LENGTH_SHORT).show()
}

fun Fragment.registerForEvents() {
    if (!EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().register(this)
    }
}

fun Fragment.unregisterFromEvents() {
    if (EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().unregister(this)
    }
}