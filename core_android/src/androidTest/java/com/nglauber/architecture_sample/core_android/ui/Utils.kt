package com.nglauber.architecture_sample.core_android.ui

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.SemanticsMatcher
import com.nglauber.architecture_sample.core_android.ui.components.DrawableId

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)