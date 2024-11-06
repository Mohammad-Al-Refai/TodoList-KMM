package com.example.todolistkmm.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistkmm.TodoListActions
import com.example.todolistkmm.TodoListItem
import com.example.todolistkmm.TodoListReducer
import com.example.todolistkmm.TodoListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var reducer = TodoListReducer()


    init {
        viewModelScope.launch {
            reducer.state.collect{
                println(it.items)
            }
        }
    }

    fun addItem() {
        reducer.dispatch(TodoListActions.AddItem)
    }

    fun deleteItem(item: TodoListItem) {
        reducer.dispatch(TodoListActions.DeleteItem(item))
    }

    fun toggle(item: TodoListItem) {
        if (item.isCompleted) {
            reducer.dispatch(TodoListActions.UnComplete(item))
        } else {
            reducer.dispatch(TodoListActions.Complete(item))
        }
    }


    fun updateTextInputValue(value: String) {
        reducer.dispatch(TodoListActions.UpdateInputValue(value))
    }

    @Composable
    fun subscribe() = reducer.state.collectAsState()

}