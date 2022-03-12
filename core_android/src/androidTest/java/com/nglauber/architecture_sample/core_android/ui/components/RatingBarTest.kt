package com.nglauber.architecture_sample.core_android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import com.nglauber.architecture_sample.core_android.R
import com.nglauber.architecture_sample.core_android.ui.hasDrawable
import org.junit.Rule
import org.junit.Test

class RatingBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ratingBarValue() {
        composeTestRule.setContent {
            Column {
                for (ratingIndex in 0..10) {
                    RatingBar(
                        rating = ratingIndex / 2f,
                        onChange = {},
                        modifier = Modifier.testTag("RatingBar$ratingIndex")
                    )
                }
            }
        }
        for (ratingIndex in 0..10) {
            val ratingBarNode = composeTestRule
                .onNodeWithTag("RatingBar$ratingIndex")
            val value = ratingIndex / 2f
            for (star in 1..5) {
                val ratingResource =
                    if (value > star - 1 && value < star) {
                        R.drawable.ic_star_half
                    } else if (value >= star) {
                        R.drawable.ic_star
                    } else {
                        R.drawable.ic_star_border
                    }
                ratingBarNode
                    .onChildAt(star - 1)
                    .assert(
                        hasDrawable(ratingResource)
                    )
            }
        }
    }
}