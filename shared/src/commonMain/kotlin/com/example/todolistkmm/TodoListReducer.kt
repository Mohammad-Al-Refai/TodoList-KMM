package com.example.todolistkmm

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class TodoListState(var items: List<TodoListItem>, var textInputValue: String)


class TodoListReducer(
    private var scope: CoroutineScope = provideMainScope(),
    private var dispatcher: CoroutineDispatcher = mainDispatcher
) {
    private var _state = MutableStateFlow(TodoListState(mutableListOf(), ""))
    var state = _state.asStateFlow()

    constructor() : this(provideMainScope(), mainDispatcher)

    // for Swift
    fun subscribe(onUpdate: (TodoListState) -> Unit) {
        scope.launch {
            state.collectLatest {
                onUpdate(it)
            }
        }
    }

    fun dispatch(action: TodoListActions) {
        scope.launch(dispatcher) {
            when (action) {
                is TodoListActions.AddItem -> {
                    _state.update { currentState ->
                        val newItems =
                            currentState.items + TodoListItem(currentState.textInputValue)
                        currentState.copy(items = newItems, textInputValue = "")
                    }
                }

                is TodoListActions.Complete -> {
                    _state.update { currentState ->
                        val updatedItems = currentState.items.map { item ->
                            if (item == action.payload) item.copy(isCompleted = true) else item
                        }
                        currentState.copy(items = updatedItems)
                    }
                }

                is TodoListActions.DeleteItem -> {
                    _state.update { currentState ->
                        val updatedItems = currentState.items.filter { it != action.payload }
                        currentState.copy(items = updatedItems)
                    }
                }

                is TodoListActions.UnComplete -> {
                    _state.update { currentState ->
                        val updatedItems = currentState.items.map { item ->
                            if (item == action.payload) item.copy(isCompleted = false) else item
                        }
                        currentState.copy(items = updatedItems)
                    }
                }

                is TodoListActions.UpdateInputValue -> {
                    if (action.payload.length<15){
                        _state.update { currentState ->
                            currentState.copy(textInputValue = action.payload)
                        }
                    }

                }
            }
        }
    }

}