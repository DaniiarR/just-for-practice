package com.example.justforpractice

import android.widget.Toast
import androidx.fragment.app.Fragment

interface OnItemClickListener {
    fun <T> onItemClick(listItem: T)
}

fun Fragment.toast(message: String?) {
    Toast.makeText(this.requireContext(), message ?: "An error occurred.", Toast.LENGTH_SHORT).show()
}