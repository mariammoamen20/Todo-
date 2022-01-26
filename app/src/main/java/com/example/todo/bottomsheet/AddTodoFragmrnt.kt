package com.example.todo.bottomsheet

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.todo.R
import com.example.todo.database.DataBase
import com.example.todo.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddTodoFragmrnt : BottomSheetDialogFragment() {
    lateinit var enter_your_task: TextInputLayout
    lateinit var enter_your_details: TextInputLayout
    lateinit var select_date: TextView
    lateinit var add_task: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_todo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize views
        initViews()

        //select date from date picker
        selectDate()

        validationOnEditText()

        //add new task
        addNewTask()
    }

    private fun addNewTask() {
        add_task.setOnClickListener {
            //lw validation true ha5od el title ely fe title text w details
            if (validationOnEditText()) {
                val title_input_layout = enter_your_task.editText?.text.toString()
                val details_input_layout = enter_your_details.editText?.text.toString()
                //function bta5od el title w details
                inserTodoInDateBase(title_input_layout, details_input_layout)
            }
        }
    }

    private fun inserTodoInDateBase(titleInputLayout: String, detailsInputLayout: String) {
        //5adt el data bta3t el todo
        val add_task = Todo(
            name = titleInputLayout,
            details = detailsInputLayout,
            data = calender.time
        )
        //harw7 adefa fe database
        DataBase.getInstance(requireContext().applicationContext).todoDao().insertTodo(add_task)
        Toast.makeText(requireContext(), "task was added successfully! ", Toast.LENGTH_SHORT).show()
        dismiss()
    }


    private fun validationOnEditText(): Boolean {
        var is_valid = true
        add_task.setOnClickListener {
            if (enter_your_task.editText?.text.toString().isBlank()) {
                enter_your_task.error = "Please enter todo title! "
                is_valid = false
            } else {
                enter_your_task.error = null
            }
            if (enter_your_details.editText?.text.toString().isBlank()) {
                enter_your_details.error = "Please enter todo details! "
                is_valid = false
            } else {
                enter_your_details.error = null
            }
        }
        return is_valid
    }

    val calender = Calendar.getInstance()
    private fun showDatePicker() {
        val date_picker = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calender.set(Calendar.MONTH, month)
                calender.set(Calendar.YEAR, year)
                select_date.text = "" + dayOfMonth + "/" + (month + 1) + "/" + year
            },
            calender[Calendar.YEAR],
            calender[Calendar.MONTH],
            calender[Calendar.DAY_OF_MONTH]
        )
        date_picker.show()
    }

    private fun selectDate() {
        select_date.text =
            "" + calender[Calendar.DAY_OF_MONTH] + "/" +
                    (calender[Calendar.MONTH] + 1) + "/" +
                    calender[Calendar.YEAR]
        select_date.setOnClickListener {
            showDatePicker()
        }
    }

    private fun initViews() {
        enter_your_task = requireView().findViewById(R.id.enter_your_task_input_layout)
        enter_your_details = requireView().findViewById(R.id.enter_your_details_input_layout)
        select_date = requireView().findViewById(R.id.select_date_text)
        add_task = requireView().findViewById(R.id.add_task_button)
    }

}