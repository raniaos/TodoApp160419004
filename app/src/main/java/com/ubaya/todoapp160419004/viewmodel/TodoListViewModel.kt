package com.ubaya.todoapp160419004.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubaya.todoapp160419004.model.Todo
import com.ubaya.todoapp160419004.model.TodoDatabase
import com.ubaya.todoapp160419004.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TodoListViewModel (application: Application) : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            //week 9
            val db = buildDb(getApplication())
            //week 8
//            val db = Room.databaseBuilder(
//                getApplication(),
//                TodoDatabase::class.java, "newtododb").build()

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    //menghapus ssebuah todoo dari database
    fun clearTask(todo: Todo) {
        launch {
            //week 9
            val db = buildDb(getApplication())
            //week 8
//            val db = Room.databaseBuilder(
//                getApplication(),
//                TodoDatabase::class.java, "newtododb").build()
            db.todoDao().deleteTodo(todo)

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun updateDone(title:String, notes:String, priority:Int, is_done:Int, uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(uuid, title, notes, priority, is_done)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }
}