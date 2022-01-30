package com.example.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.bottomsheet.clearTime
import com.example.todo.database.DataBase
import com.example.todo.model.Todo
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*

class TodoListFragment : Fragment() {
    lateinit var todo_list_recycler_view: RecyclerView
    lateinit var matriel_calender_view: MaterialCalendarView
    lateinit var get_all_todo_list: MutableList<Todo>
    val calendar = Calendar.getInstance()
    val todo_list_adapter = TodoListAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        todo_list_recycler_view = requireView().findViewById(R.id.todo_recycler_view)
        matriel_calender_view = requireView().findViewById(R.id.todo_calender_view)
        matriel_calender_view.selectedDate = CalendarDay.today()
        todo_list_recycler_view.adapter = todo_list_adapter
        todo_list_adapter.on_todo_item_click = object : TodoListAdapter.OnTodoItemClick {
            override fun onTodoClick(position: Int, todo: Todo) {
                get_all_todo_list.removeAt(position)
                todo_list_adapter.notifyItemRemoved(position)
                todo_list_adapter.notifyDataSetChanged()

                DataBase.getInstance(requireContext()).todoDao()
                    .deleteTodo(todo)

                Toast.makeText(requireContext(),"Todo Was Deleted Successfully",Toast.LENGTH_SHORT).show()

            }

        }
        matriel_calender_view.setOnDateChangedListener { widget, calender_day, selected ->
            calendar.set(Calendar.DAY_OF_MONTH, calender_day.day)
            calendar.set(Calendar.MONTH, calender_day.month - 1)
            calendar.set(Calendar.YEAR, calender_day.year)
            getAllTodoFromDataBase()

        }


    }

    override fun onResume() {
        super.onResume()
        getAllTodoFromDataBase()
    }

    fun getAllTodoFromDataBase() {
        get_all_todo_list = DataBase.getInstance(requireContext().applicationContext)
            .todoDao().getTodoByDate(calendar.clearTime().time)
        todo_list_adapter.changeDate(get_all_todo_list)
    }
}