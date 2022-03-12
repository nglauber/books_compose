package com.nglauber.architecture_sample.core_android.ui.theme.custom

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.nglauber.architecture_sample.core_android.R

@Immutable
data class AppTypography(
    val materialTypography: Typography
) {
    val h1: TextStyle
        get() = materialTypography.h1

    val h2: TextStyle
        get() = materialTypography.h2

    val h3: TextStyle
        get() = materialTypography.h3

    val h4: TextStyle
        get() = materialTypography.h4

    val h5: TextStyle
        get() = materialTypography.h5

    val h6: TextStyle
        get() = materialTypography.h6

    val subtitle1: TextStyle
        get() = materialTypography.subtitle1

    val subtitle2: TextStyle
        get() = materialTypography.subtitle2

    val body1: TextStyle
        get() = materialTypography.body1

    val body2: TextStyle
        get() = materialTypography.body2

    val button: TextStyle
        get() = materialTypography.button

    val caption: TextStyle
        get() = materialTypography.caption

    val overline: TextStyle
        get() = materialTypography.overline

    // App custom TextStyles

    companion object {
        fun appTypography(): AppTypography {
            val fontFamily = AppFontFamilies.Oxygen
            val defaultTypography = Typography()
            return AppTypography(
                materialTypography = Typography(
                    h1 = defaultTypography.h1.copy(fontFamily = fontFamily),
                    h2 = defaultTypography.h2.copy(fontFamily = fontFamily),
                    h3 = defaultTypography.h3.copy(fontFamily = fontFamily),
                    h4 = defaultTypography.h4.copy(fontFamily = fontFamily),
                    h5 = defaultTypography.h5.copy(fontFamily = fontFamily),
                    h6 = defaultTypography.h6.copy(fontFamily = fontFamily),
                    subtitle1 = defaultTypography.subtitle1.copy(fontFamily = fontFamily),
                    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = fontFamily),
                    body1 = defaultTypography.body1.copy(fontFamily = fontFamily),
                    body2 = defaultTypography.body2.copy(fontFamily = fontFamily),
                    button = defaultTypography.button.copy(fontFamily = fontFamily),
                    caption = defaultTypography.caption.copy(fontFamily = fontFamily),
                    overline = defaultTypography.overline.copy(fontFamily = fontFamily)
                )
            )
        }

        @Immutable
        object AppFontFamilies {
            @Stable
            val Oxygen = FontFamily(
                Font(
                    resId = R.font.oxygen_regular,
                    weight = FontWeight.Normal,
                    style = FontStyle.Normal
                ),
                Font(
                    resId = R.font.oxygen_bold,
                    weight = FontWeight.Bold,
                    style = FontStyle.Normal
                ),
                Font(
                    resId = R.font.oxygen_light,
                    weight = FontWeight.Light,
                    style = FontStyle.Normal
                ),
            )
        }
    }
}