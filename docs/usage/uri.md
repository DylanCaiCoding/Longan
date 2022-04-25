# Uri 用法

## 查询多媒体文件

查询多媒体视频的示例：

```kotlin
// Container for information about each video.
data class Video(
  val uri: Uri,
  val name: String,
  val duration: Int,
  val size: Int
)

val videoList = mutableListOf<Video>()

val projection = arrayOf(
  MediaStore.Video.Media._ID,
  MediaStore.Video.Media.DISPLAY_NAME,
  MediaStore.Video.Media.DURATION,
  MediaStore.Video.Media.SIZE
)

contentResolver.queryMediaVideos(projection) { cursor ->
  val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
  val nameColumn =  cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
  val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
  val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
  while (cursor.moveToNext()) {
    val id = cursor.getLong(idColumn)
    val name = cursor.getString(nameColumn)
    val duration = cursor.getInt(durationColumn)
    val size = cursor.getInt(sizeColumn)
    val contentUri: Uri = ContentUris.withAppendedId(EXTERNAL_MEDIA_VIDEO_URI, id)
    videoList += Video(contentUri, name, duration, size)
  }
}
```

## 添加多媒体文件

```kotlin
val audioUri = contentResolver.insertMediaAudio()

val videoUri = contentResolver.insertMediaVideo(
  MediaStore.Video.Media.DISPLAY_NAME to "emergency.mp4",
  MediaStore.Video.Media.DESCRIPTION to "This is an emergency video."
)
```

## 更新多媒体文件

```kotlin
audioUri.update(MediaStore.Audio.Media.DISPLAY_NAME to "My Workout Playlist.mp3")
```

## 删除多媒体文件

```kotlin
contentResolver.delete(uri)
```

## File 转 Uri

在 `AndroidManifest` 配置 FileProvider。

```xml
<provider
  android:name="androidx.core.content.FileProvider"
  android:authorities="${applicationId}.fileProvider"
  android:exported="false"
  android:grantUriPermissions="true">
  <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths" />
</provider>
```

初始化 `android:authorities` 对应的值，`${applicationId}` 对应的是包名。

```kotlin
fileProviderAuthority = "$packageName.fileProvider"
```

然后就可以将 File 转为 Uri，可获取在应用专属目录的文件的 Uri。

```kotlin
val uri = File(externalCacheDirPath, "avatar.jpg").toUri()
```