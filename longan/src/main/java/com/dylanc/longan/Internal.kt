/*
 * Copyright (c) 2021. Dylan Cai
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

package com.dylanc.longan

import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

internal const val NO_GETTER: String = "Property does not have a getter"

internal fun noGetter(): Nothing = throw NotImplementedError(NO_GETTER)

internal var View.isAddedMarginTop: Boolean? by viewTags(R.id.tag_is_added_margin_top)
internal var View.isAddedPaddingTop: Boolean? by viewTags(R.id.tag_is_added_padding_top)
internal var View.isAddedMarginBottom: Boolean? by viewTags(R.id.tag_is_added_margin_bottom)
internal var View.lastClickTime: Long? by viewTags(R.id.tag_last_click_time)
internal var View.rootWindowInsetsCompatCache: WindowInsetsCompat? by viewTags(R.id.tag_root_window_insets)
internal var View.windowInsetsControllerCompatCache: WindowInsetsControllerCompat? by viewTags(R.id.tag_window_insets_controller)
