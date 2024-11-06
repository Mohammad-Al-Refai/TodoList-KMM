//
//  ViewModel.swift
//  iosApp
//
//  Created by Mohammad on 11/6/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared


class ViewModel: ObservableObject{
    var reducer = TodoListReducer()
    @Published var items:[TodoListItem] = []
    @Published var inputValue = ""
    
    init(){
        collectState()
    }
   private func collectState(){
        reducer.subscribe(onUpdate: { [weak self] (newState: TodoListState) in
            DispatchQueue.main.async {
                print("New Update")
                self?.items = newState.items
                self?.inputValue = newState.textInputValue
            }
        })
    }
    
    
    func addItem(){
        reducer.dispatch(action: TodoListActions.AddItem())
    }
    func deleteItem(item:TodoListItem){
        reducer.dispatch(action: TodoListActions.DeleteItem(payload: item))
    }
    func toggleItem(item:TodoListItem){
        if item.isCompleted{
            reducer.dispatch(action: TodoListActions.UnComplete(payload: item))
        }else{
            reducer.dispatch(action: TodoListActions.Complete(payload: item))
        }
        
    }
}
