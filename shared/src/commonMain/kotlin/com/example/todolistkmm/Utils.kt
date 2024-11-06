package com.example.todolistkmm

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

expect fun provideMainScope(): CoroutineScope
expect val mainDispatcher: CoroutineDispatcher