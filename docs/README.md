# Longan

[![](https://www.jitpack.io/v/DylanCaiCoding/Longan.svg)](https://www.jitpack.io/#DylanCaiCoding/Longan) 
[![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/Longan/blob/master/LICENSE) 
[![GitHub Repo stars](https://img.shields.io/github/stars/DylanCaiCoding/Longan?style=social)](https://github.com/DylanCaiCoding/Longan)

Longan 是一个简化 Android 开发的 Kotlin 工具库，可以使代码更加简洁易读。**目前有超过 500 个常用方法或属性，能有效提高开发效率**。

个人认为 API 设计对 Kotlin 工具类来说非常重要，因为 Kotlin 玩法很多，可以设计出很骚的用法，但不一定好用。所以个人花了大量时间去斟酌每一个扩展的用法，每个命名和用法都是经过了非常多的考虑，既要简洁易用又要清晰地描述功能不产生歧义。有的会参考官方 KTX 库的命名规则和用法，与官方用法统一能降低点学习成本。会用尽可能简洁轻量的代码实现功能，有兴趣的可以读下源码。整体会比大多数人封装的工具类好用。

本库会长期维护，有任何使用上的问题或者想要的功能都可以反馈。

## Gradle

在根目录的 build.gradle 添加：

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

添加依赖：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding.Longan:longan:1.1.1'
    // Optional
    implementation 'com.github.DylanCaiCoding.Longan:longan-design:1.1.1'
}
```

## 更新日志

[Releases](https://github.com/DylanCaiCoding/Longan/releases)

## 作者其它的库

| 库                                                           | 简介                                           |
| ------------------------------------------------------------ | ---------------------------------------------- |
| [LoadingStateView](https://github.com/DylanCaiCoding/LoadingStateView) | 深度解耦标题栏或加载中、加载失败、无数据等视图 |
| [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX)     | 最全面的 ViewBinding 工具                |
| [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX)                 | 用属性委托的方式使用 MMKV                  |
| [Tracker](https://github.com/DylanCaiCoding/Tracker)         | 基于西瓜视频的责任链埋点思路实现的轻量级埋点框架          |

## License

```
Copyright (C) 2021. Dylan Cai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
