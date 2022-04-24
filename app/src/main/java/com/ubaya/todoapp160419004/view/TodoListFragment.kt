package com.ubaya.todoapp160419004.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.todoapp160419004.R
import com.ubaya.todoapp160419004.viewmodel.TodoListViewModel
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment : Fragment() {
    private lateinit var viewModel:TodoListViewModel
//    bisa atas bisa bawah
//    private val todoListAdapter  = TodoListAdapter(arrayListOf(), { item -> viewModel.clearTask(item)})
    private val todoListAdapter  = TodoListAdapter(arrayListOf(), { viewModel.updateDone(it.title, it.notes, it.priority, 1, it.uuid)})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        viewModel.refresh()
        recViewTodo.layoutManager = LinearLayoutManager(context)
        recViewTodo.adapter = todoListAdapter

        fabAddTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()

    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            if(it.isEmpty()) {
                textEmpty.visibility = View.VISIBLE
            } else {
                textEmpty.visibility = View.GONE
            }
        })
    }

}