# Fragment 用法

### 创建带参数的 Fragment

传参给 Fragment 需要设置 Bundle 对象，通常 Bundle 对象每增加一个额外的参数，就需要增加一行代码。例如：

```kotlin
val fragment = SomeFragment()
val arguments = Bundle()
arguments.putInt("id", 5)
arguments.putString("title", title)
fragment.arguments = arguments
```

而 `Longan` 提供了更简单的方式实现上面的功能。

```kotlin
val fragment = SomeFragment().withArguments("id" to 5, "title" to title)
```

### Fragment 的 arguments 委托

使用 `arguments()` 委托获取传递给 Fragment 的数据。

```kotlin
val name: String? by arguments(KEY_NAME)
val count: Int by arguments(KEY_COUNT, default = 0)
```

如果可以人为确保一定能获取到值，又不想传默认值，可以用 `safeArguments()` 进行委托。

```kotlin
val phone: String by safeArguments(KEY_PHONE)
```

创建带参数 Fragment 的推荐写法：

```kotlin
class SomeFragment : Fragment() {
  private val id: String by safeArguments(KEY_ID)

  // ...

  companion object {
    fun newInstance(id: String) = SomeFragment().withArguments(KEY_ID to id)
  }
}
```

```kotlin
val fragment = SomeFragment.newInstance(id)
```

编写一个静态的 `newInstance()` 方法传入所需的参数并创建 Fragment，在 Fragment 内通过委托的方式获取值。调用静态方法可以防止传漏或传错参数。
