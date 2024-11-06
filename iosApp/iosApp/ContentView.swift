import shared
import SwiftUI

struct ContentView: View {
    @StateObject var vm = ViewModel()

    var body: some View {
        VStack {
            HStack {
                TextField("", text: $vm.inputValue).onChange(of: vm.inputValue) { newValue in
                    vm.reducer.dispatch(action: .UpdateInputValue(payload: newValue))
                }.font(.body)
                Button("Add") {
                    vm.addItem()
                }
            }.padding()
            LazyVGrid(columns: [GridItem()]) {
                ForEach(0 ..< vm.items.count, id: \.self) { index in
                    let item = vm.items[index]
                    HStack(alignment: .center, spacing: 20){
                        if item.isCompleted{
                            Text(vm.items[index].content).strikethrough()
                        }else{
                            Text(vm.items[index].content)
                        }
                        Spacer()
                        Button(item.isCompleted ? "UnComplete":"Complete") {
                            vm.toggleItem(item: item)
                        }
                        Button("Delete") {
                            vm.deleteItem(item: item)
                        }
                    }.padding()
                }
            }
            Spacer()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
