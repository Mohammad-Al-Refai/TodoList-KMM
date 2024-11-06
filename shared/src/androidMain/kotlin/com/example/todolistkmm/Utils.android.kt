package com.example.todolistkmm

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope

actual fun provideMainScope(): CoroutineScope = MainScope()
actual val mainDispatcher: CoroutineDispatcher = Dispatchers.Main