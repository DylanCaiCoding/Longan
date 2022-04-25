# SystemBars 用法

使用最新的 `WindowInsets` 实现，兼容了 Android 10。

## SystemBars

应用全屏：

```kotlin
isFullScreen = true
```

## StatusBar

实现沉浸式状态栏，并且给标题栏的顶边距增加状态栏高度，可以适配刘海水滴屏：

```kotlin
immerseStatusBar(lightMode = false)
toolbar.addStatusBarHeightToMarginTop()
// toolbar.addStatusBarHeightToPaddingTop()
```

透明状态栏：

```kotlin
transparentStatusBar()
```

其它状态栏属性：

- `statusBarColor`，设置或获取状态栏颜色
- `isLightStatusBar`，设置或判断状态栏是否为浅色模式
- `isStatusBarVisible`，设置或判断状态栏是否为显示
- `statusBarHeight`，获取状态栏高度

## NavigationBar

给控件的底边距增加导航栏高度：

```kotlin
view.addNavigationBarHeightToMarginBottom()
// view.addNavigationBarHeightToPaddingBottom()
```

其它状态栏属性：

- `navigationBarColor`，设置或获取导航栏颜色
- `isLightNavigationBar`，设置或判断导航栏是否为浅色模式
- `isNavigationBarVisible`，设置或判断导航栏是否为显示
- `navigationBarHeight`，获取导航栏高度