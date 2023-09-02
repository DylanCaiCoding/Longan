/*
 * Copyright (c) 2021. Reone
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.longan

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process

fun killProcess(pid: Int = Process.myPid()) {
    Process.killProcess(pid)
}

val Context.currentProcessName: String?
    get() {
        if (!cacheName.isNullOrEmpty()) {
            return cacheName
        }
        cacheName = nameFromApplication ?: nameFromActivityThread ?: nameFromActivityManager
        return cacheName
    }

private var cacheName: String? = null
private val nameFromApplication: String?
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) Application.getProcessName() else null
private val nameFromActivityThread: String?
    @SuppressLint("PrivateApi")
    get() {
        return runCatching {
            var processName: String?
            Class.forName("android.app.ActivityThread", false, Application::class.java.classLoader)
                .getDeclaredMethod("currentProcessName", *arrayOfNulls<Class<*>?>(0)).let {
                    it.isAccessible = true
                    processName = it.invoke(null, *arrayOfNulls(0)) as String
                }
            return processName
        }.getOrNull()
    }
private val Context.nameFromActivityManager: String?
    get() {
        val pid = Process.myPid()
        (context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)?.runningAppProcesses?.forEach {
            if (it.pid == pid) {
                return it.processName
            }
        }
        return null
    }