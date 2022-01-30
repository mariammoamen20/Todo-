package com.example.todo.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Todo

class TodoListAdapter(var todo_list_item : MutableList<Todo>?) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>(){
    class TodoListViewHolder(item_view : View) : RecyclerView.ViewHolder(item_view){
        val todo_list_title: TextView= item_view.findViewById(R.id.todo_title)
        val todo_list_details :TextView = item_view.findViewById(R.id.todo_details)
        //val mark_as_done : ImageView = item_view.findViewById(R.id.mark_as_done_btn)
        val delete_image : ImageView= item_view.findViewById(R.id.right_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
    val todo_view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false,)
        return TodoListViewHolder(todo_view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todo_items = todo_list_item!!.get(position)
        holder.todo_list_title.text=todo_items?.name
        holder.todo_list_details.text=todo_items?.details
        //holder.mark_as_done.setImageResource(todo_items.is_done)

        if(on_todo_item_click!=null){
            holder.delete_image.setOnClickListener {
                on_todo_item_click?.onTodoClick(position,todo_items)
            }
        }

    }

    override fun getItemCount(): Int = todo_list_item?.size?:0


    fun changeDate(new_item: MutableList<Todo>){
        todo_list_item = new_item
        notifyDataSetChanged()
    }
    var on_todo_item_click :OnTodoItemClick?=null;
    interface OnTodoItemClick{
        fun onTodoClick(position : Int , todo: Todo)
    }
}