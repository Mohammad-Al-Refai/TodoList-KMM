package com.example.todolistkmm.android

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm by viewModels<MainViewModel>()

        setContent {
            val state = vm.subscribe()
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {

                                TextField(value = state.value.textInputValue,
                                    onValueChange = {
                                        vm.updateTextInputValue(it)
                                    }, singleLine = true, maxLines = 1)

                            Button(onClick = vm::addItem) {
                                Text("Add")
                            }
                        }
                        if (state.value.items.isEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text("No items!")
                        } else {
                            LazyColumn {
                                items(state.value.items.size) {
                                    val item = state.value.items[it]
                                    ListItem(headlineContent = {
                                        Text(
                                            item.content,
                                            textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                        )
                                    }, trailingContent = {
                                        Row {
                                            Button(onClick = {
                                                vm.toggle(item)
                                            }) {
                                                if (item.isCompleted) Text("UnComplete") else Text("Complete")
                                            }
                                            IconButton(onClick = {
                                                vm.deleteItem(item)
                                            }) {
                                                Icon(Icons.Rounded.Delete, contentDescription = "")
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


