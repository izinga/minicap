/*
 * Copyright (C) 2020 Orange
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.devicefarmer.minicap.utils

import android.util.Size

/**
 * Get DisplayInfo through DisplayManagerGlobal singleton (similar to what is done in MinitouchAgent)
 */
object DisplayManagerGlobal {
    private var displayManager: Any?
    private var CLASS: Class<*>?

    init {
        try {
            CLASS = Class.forName("android.hardware.display.DisplayManagerGlobal")
            val getInstance = CLASS!!.getMethod("getInstance")
            displayManager = getInstance.invoke(null)
        } catch (e: ClassNotFoundException) {
            throw AssertionError(e)
        }
    }


    fun getDisplayInfo(displayId: Int): DisplayInfo {
        return try {
            val displayInfo =
                displayManager!!.javaClass.getMethod("getDisplayInfo", Int::class.javaPrimitiveType)
                    .invoke(displayManager, displayId)
                val cls: Class<*> = displayInfo.javaClass
                val width = cls.getDeclaredField("logicalWidth").getInt(displayInfo)
                val height = cls.getDeclaredField("logicalHeight").getInt(displayInfo)
                val rotation = cls.getDeclaredField("rotation").getInt(displayInfo)
                val layerStack = cls.getDeclaredField("layerStack").getInt(displayInfo)
                DisplayInfo(displayId, Size(width, height), rotation, layerStack)
            } catch (e: Exception) {
                throw AssertionError(e)
            }
    }
}
