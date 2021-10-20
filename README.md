# Longan

English | [中文](README_CN.md)

[![](https://www.jitpack.io/v/DylanCaiCoding/Longan.svg)](https://www.jitpack.io/#DylanCaiCoding/Longan) [![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/Longan/blob/master/LICENSE)

Longan is a collection of Kotlin utils which makes Android application development faster and easier. It makes your code clean and easy to read.


## Gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

Add dependencies：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding.Longan:longan:1.0.0'
    // Optional
    implementation 'com.github.DylanCaiCoding.Longan:longan-design:1.0.0'
}
```

## Wiki

### All APIs

- [Common APIs](https://github.com/DylanCaiCoding/Longan/wiki/All-Common-APIs)
- [Design APIs](https://github.com/DylanCaiCoding/Longan/wiki/All-Design-APIs)

### Some usage

- [Activity](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Activity)
- [Dialogs](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Dialogs)
- [Fragment](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Fragment)
- [Intents](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Intents)
- [Logger](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Logger)
- [SystemBars](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-SystemBars)
- [Uri](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Uri)

## Change log

[Releases](https://github.com/DylanCaiCoding/Longan/releases)

## Author's other libraries

| Library                                                      | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [LoadingStateView](https://github.com/DylanCaiCoding/LoadingStateView) | A highly expandable Android library for decoupling the code of toolbar or loading status view. |
| [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX) | The most comprehensive utils of ViewBinding.                 |
| [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX)       | Easier to use the MMKV.                                      |
| [ActivityResultLauncher](https://github.com/DylanCaiCoding/ActivityResultLauncher) | Perfect replacement for `startActivityForResult()`           |

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
