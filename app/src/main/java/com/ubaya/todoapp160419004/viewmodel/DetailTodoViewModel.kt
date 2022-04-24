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

class DetailTodoViewModel(application:  Application) : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<Todo>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun addTodo(list: List<Todo>) {
        launch {
            //week 9
            val db = buildDb(getApplication())
            //week 8
//            val db = Room.databaseBuilder(
//                getApplication(), TodoDatabase::class.java,
//                "newtododb"
//            ).build()
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }

    fun fetch(uuid: Int) {
        launch {
            val db = buildDb(getApplication())
            todoLD.value = db.todoDao().selectTodo(uuid)
        }
    }

    fun update(title:String, notes:String, priority:Int, is_done:Int, uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(uuid, title, notes, priority, is_done)
        }
    }
}
