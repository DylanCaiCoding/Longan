# ViewModels 用法

### 获取 Application 级别 ViewModel

消息事件传递推荐 [Jetpack-MVVM-Best-Practice](https://github.com/KunMinX/Jetpack-MVVM-Best-Practice) 的方案，用共享 ViewModel 持有的 LiveData 进行分发，避免消息推送难以溯源、消息同步不可靠不一致等问题。由于 LiveData 存在依赖倒灌的问题，一般会自行封装 EventLiveData 用于事件的场景。但是不考虑 Java 的话，可以直接用协程的 SharedFlow。

```kotlin
class SharedViewModel : ViewModel() {
  val saveNameEvent = MutableSharedFlow<String>()
}
```

通过 by applicationViewModels() 获取 Application 级别的 ViewModel，实现共享 ViewModel：

```kotlin
private val sharedViewModel: SharedViewModel by applicationViewModels()

// 发送事件
sharedViewModel.saveNameEvent.tryEmit(name)

// 监听事件，提供了类似 LiveData 的 observe 用法，简化 collect 的代码
sharedViewModel.saveNameEvent.launchAndCollectIn(this) {
  finish()
}
```
