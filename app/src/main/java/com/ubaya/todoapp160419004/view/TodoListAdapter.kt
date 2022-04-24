package com.ubaya.todoapp160419004.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.todoapp160419004.R
import com.ubaya.todoapp160419004.model.Todo
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick : (Todo) -> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view:View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        with (holder.view) {
            checkTask.text = todo.title
            checkTask.setOnCheckedChangeListener {
                compoundButton, value -> if(value) adapterOnClick(todo)
            }
            buttonEdit.setOnClickListener {
                val action = TodoListFragmentDirections.actionEditTodoFragment(todoList[position].uuid)
                Navigation.findNavController(it).navigate(action)
            }
        }
        // holder.view.checkTask.setText(todoList[position].title.toString())
    }

    override fun getItemCount() = todoList.size

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}
