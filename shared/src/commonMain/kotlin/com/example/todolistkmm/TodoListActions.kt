package com.example.todolistkmm

sealed class TodoListActions {
    object AddItem : TodoListActions()
    data class DeleteItem(var payload: TodoListItem) : TodoListActions()
    data class Complete(var payload: TodoListItem) : TodoListActions()
    data class UnComplete(var payload: TodoListItem) : TodoListActions()
    data class UpdateInputValue(var payload: String) : TodoListActions()
}