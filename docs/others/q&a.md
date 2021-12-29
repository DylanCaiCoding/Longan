# Q&A

### 为什么叫 `Longan`？

个人想用个水果名来作为库名，最初想到的是 `Guava` （石榴），感觉非常有代表性。但是发现有一个谷歌的同名库，所以换了另一个也是多子的水果 `Longan` （龙眼）。

### 为什么没有时间和日期工具？

本来用 Java8 的 `LocalDataTime` 封装了日期和时间的工具类，但是发现 Kotlin 官方有个日期时间库 [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime)，个人更推荐用官方的库，就把相关的封装给移除了。后续会考虑对此库进行二次封装再进一步简化代码。