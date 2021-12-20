# Design APIs

## Gradle

添加依赖到 `build.gradle`：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding.Longan:longan-design:1.0.2'
}
```

## APIs

下面用法中含有方括号 `[]` 的参数是可选的。

### [Dialogs.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan-design/src/main/java/com/dylanc/longan/design/Dialogs.kt)

| 用法                                                         | 作用               |
| ------------------------------------------------------------ | ------------------ |
| `Context/Fragment.alert([factory], message, [title]) {...}`  | 显示消息弹框       |
| `Context/Fragment.selector([factory], items, [title]) {...}` | 显示选项弹框       |
| `Context/Fragment.singleChoiceSelector([factory], items, checkIndex, [title]) {...}` | 显示单选弹框       |
| `Context/Fragment.singleChoiceSelector([factory], items, checkItems, [title]) {...}` | 显示多选弹框       |
| `Context/Fragment.alertDialog([factory]） {...}`             | 显示弹框           |
| `initAlertBuilderFactory(factory)`                           | 初始化默认弹框样式 |
| `Dialog/DialogInterface.doOnCancel {...}`                    | 监听取消事件       |
| `Dialog/DialogInterface.doOnDismiss{...}`                    | 监听消失事件       |
| `Dialog/DialogInterface.doOnShow{...}`                       | 监听显示事件       |

### [RecyclerView.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan-design/src/main/java/com/dylanc/longan/design/RecyclerView.kt)

| 用法                                                    | 作用                 |
| ------------------------------------------------------- | -------------------- |
| `RecyclerView.setEmptyView(owner, emptyView)`           | 设置空布局           |
| `RecyclerView.Adapter<*>.observeDataEmpty(owner) {...}` | 观察数据是否为空     |
| `RecyclerView.smoothScrollToStartPosition(position)`    | 顺滑地滚动到起始位置 |
| `RecyclerView.smoothScrollToEndPosition(position)`      | 顺滑地滚动到末端位置 |

### [Snackbar.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan-design/src/main/java/com/dylanc/longan/design/Snackbar.kt)

| 用法                                                         | 作用                |
| ------------------------------------------------------------ | ------------------- |
| `Activity/Fragment/View.snackbar(message, [actionText], [action])` | 显示 Snackbar       |
| `Activity/Fragment/View.longSnackbar(message, [actionText], [action])` | 长时间显示 Snackbar |
| `Activity/Fragment/View.indefiniteSnackbar(message, [actionText], [action])` | 永久显示 Snackbar   |

### [TabLayout.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan-design/src/main/java/com/dylanc/longan/design/TabLayout.kt)

| 用法                                                         | 作用             |
| ------------------------------------------------------------ | ---------------- |
| `TabLayout.setupWithViewPager(viewPager, [autoRefresh]) {...}` | 设置 ViewPager   |
| `TabLayout.setupWithViewPager2(viewPager, [autoRefresh], [enableScroll]) {...}` | 设置 ViewPager2  |
| `TabLayout.Tab.setCustomView(layoutId) {...}`                | 设置自定义布局   |
| `TabLayout.addTab(text) {...}`                               | 添加标签         |
| `TabLayout.doOnTabSelected {...}`                            | 监听标签被选中   |
| `TabLayout.doOnTabUnselected {...}`                          | 监听标签取消选中 |
| `TabLayout.doOnTabReselected {...}`                          | 监听标签重新选中 |
| `TabLayout.addOnTabSelectedListener(onTabSelected, onTabUnselected, onTabReselected) ` | 监听标签选中事件 |

### [ViewPager2.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan-design/src/main/java/com/dylanc/longan/design/ViewPager2.kt)

| 用法                                                         | 作用                      |
| ------------------------------------------------------------ | ------------------------- |
| `FragmentActivity/Fragment.FragmentStateAdapter(fragments)`  | 创建 FragmentStateAdapter |
| `FragmentActivity/Fragment.FragmentStateAdapter(itemCount) {...}` | 创建 FragmentStateAdapter |
| `ViewPager2.findFragment(fragmentManager, position)`         | 获取 Fragment             |