package com.example.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Dao
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDatabase
import com.example.studentregister.db.StudentViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    private lateinit var viewModel: StudentViewModel

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adaptor : StudentRecyclerViewAdaptor
    private var isListItemClicked=false
    private lateinit var selectedStudent :Student
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.etName)
        emailEditText = findViewById(R.id.etEmail)
        saveButton = findViewById(R.id.btnSave)
        clearButton = findViewById(R.id.btnClear)
        studentRecyclerView = findViewById(R.id.rvStudent)

        val dao = StudentDatabase.getInstance(application).StudentDao()
        val factory = studentViewModelFactory(dao)
        viewModel = ViewModelProvider(owner = this, factory).get(StudentViewModel::class.java)

        saveButton.setOnClickListener {
            if(isListItemClicked)
            {
             updateStudentData()
             clearInput()
            }
            else {
                saveStudentData()
                clearInput()
            }
        }
        clearButton.setOnClickListener {
            if(isListItemClicked)
            {
                deleteStudentData()
                clearInput()
            }
            else {
                clearInput()
            }
        }
        initRecyclerView()
    }

    private fun saveStudentData() {
//        val name = nameEditText.text.toString()
//        val email = emailEditText.text.toString()
//        val student = Student(id = 0,name,email)
//        viewModel.insertStudent(student)

        viewModel.insertStudent(
            Student(
                id = 0,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
    }
    private fun updateStudentData()
    {
        viewModel.updateStudent(
            Student(
                selectedStudent.id,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
       // selectedStudent=null
        saveButton.text="save"
        clearButton.text="clear"
        isListItemClicked=false
    }

    private fun deleteStudentData()
    {
        viewModel.deleteStudent(
            Student(
                selectedStudent.id,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
        //selectedStudent=null
        saveButton.text="save"
        clearButton.text="clear"
        isListItemClicked=false
    }
    private fun clearInput() {
        nameEditText.setText("")
        emailEditText.setText("")
    }

    private fun initRecyclerView()
    {
       studentRecyclerView.layoutManager=LinearLayoutManager(this)
        adaptor= StudentRecyclerViewAdaptor{
            selectedItem:Student->listItemClicked(selectedItem)
        }
        studentRecyclerView.adapter=adaptor

        displayStudentsList()
    }

    private fun displayStudentsList()
    {
      viewModel.student.observe(this,{
          adaptor.setList(it)
          adaptor.notifyDataSetChanged()
      }
      )
    }
    private fun listItemClicked(student: Student)
    {
        /*Toast.makeText(
            this,
            "student name is ${student.name}",
            Toast.LENGTH_LONG
        ).show()*/
        selectedStudent=student
        saveButton.text="update"
        clearButton.text="delete"
        isListItemClicked=true
        nameEditText.setText(selectedStudent.name)
        emailEditText.setText(selectedStudent.email)
    }
}