package com.example.studentregister

import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentregister.db.StudentDao
import com.example.studentregister.db.StudentViewModel

class studentViewModelFactory(private val dao: StudentDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StudentViewModel::class.java))
        {
            return StudentViewModel(dao)as T
        }
        throw IllegalArgumentException("unknown View model class")
    }
}