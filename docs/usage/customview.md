# 自定义 View 用法

### 获取自定义属性

获取自定义属性需要不少代码来实现。

```kotlin
val attr = context.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0)
textSize = attr.getDimension(R.styleable.CustomView_textSize, 12.sp)
textColor = attr.getColor(R.styleable.CustomView_textColor, ContextCompat.getColor(context, R.color.text_normal))
icon = attr.getDrawable(R.styleable.CustomView_icon) ?: ContextCompat.getDrawable(context, R.drawable.default_icon)
iconSize = attr.getDimension(R.styleable.CustomView_iconSize, 30.dp)
attr.recycle()
```

所以提供了更简洁的方式来获取自定义属性：

```kotlin
withStyledAttrs(attrs, R.styleable.CustomView) {
  textSize = getDimension(R.styleable.CustomView_textSize, 12.sp)
  textColor = getColor(R.styleable.CustomView_textColor, getCompatColor(R.color.text_normal))
  icon = getDrawable(R.styleable.CustomView_icon) ?: getCompatDrawable(R.drawable.default_icon)
  iconSize = getDimension(R.styleable.CustomView_iconSize, 30.dp)
}
```

### 绘制居中文字

绘制居中或者垂直居中的文字：

```kotlin
canvas.drawCenterText(text, centerX, centerY, paint)
canvas.drawCenterVerticalText(text, centerX, centerY, paint)
```

### 触摸相关

获取当前触摸位置的子 View:

```kotlin
val touchedView = findTouchedChild(ev.rawX, ev.rawY)
```

判断某个 View 是不是在触摸位置上：
```kotlin
if (view.isTouchedAt(ev.rawX, ev.rawY)) {
  // ...
}
```