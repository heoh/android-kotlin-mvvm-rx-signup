package com.example.signupsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            SignUpViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}