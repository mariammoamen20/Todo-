package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todo.bottomsheet.AddTodoFragmrnt
import com.example.todo.list.TodoListFragment
import com.example.todo.settings.TodoSettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var bottom_navigation_view : BottomNavigationView
    lateinit var add_floating_button : FloatingActionButton
    val todo_list_fragment = TodoListFragment()
    val todo_setting_fragment = TodoSettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation_view = findViewById(R.id.bottom_navigation)
        add_floating_button = findViewById(R.id.add_floating_button)

        bottom_navigation_view.setOnItemSelectedListener {
            if(it.itemId == R.id.list_item){
                pushFragment(todo_list_fragment)
            }else if(it.itemId ==  R.id.settings_item){
                pushFragment(todo_setting_fragment)
            }
            return@setOnItemSelectedListener true
        }
        bottom_navigation_view.selectedItemId=R.id.list_item

        add_floating_button.setOnClickListener {
            //showing bottom sheet
            showButtonSheet()
        }
    }

    private fun showButtonSheet() {
        val add_to_bottom_sheet = AddTodoFragmrnt()
        add_to_bottom_sheet.show(supportFragmentManager,"")
        add_to_bottom_sheet.on_todo_added = object :AddTodoFragmrnt.OnTodoAdded{
            override fun onAdded() {
                //refresh todos list from database inside list fragment
                if(todo_list_fragment.isVisible){
                    todo_list_fragment.getAllTodoFromDataBase()
                }
            }
        }
    }

    fun pushFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,fragment)
            .commit()
    }
}