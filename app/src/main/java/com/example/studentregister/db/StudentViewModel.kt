package com.example.studentregister.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StudentViewModel(private var dao: StudentDao):ViewModel (){
    val student=dao.getAllStudent()

    fun insertStudent(student: Student)=viewModelScope.launch {
        dao.insertStudent(student)
    }
    fun updateStudent(student: Student)=viewModelScope.launch {
        dao.updateStudent(student)
    }
    fun deleteStudent(student: Student)=viewModelScope.launch {
        dao.deleteStudent(student)
    }




}