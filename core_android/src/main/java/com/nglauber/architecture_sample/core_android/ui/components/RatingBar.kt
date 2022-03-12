package com.nglauber.architecture_sample.core_android.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nglauber.architecture_sample.core_android.R
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTheme

@Composable
fun RatingBar(
    rating: Float,
    onChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    starColor: Color = AppTheme.colors.secondary,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        for (i in 1..5) {
            val resId = when {
                rating >= i ->
                    R.drawable.ic_star
                (i - rating) >= .5f && (i - rating) < 1f ->
                    R.drawable.ic_star_half
                else ->
                    R.drawable.ic_star_border
            }
            Icon(
                imageVector = ImageVector.vectorResource(id = resId),
                contentDescription = null,
                tint = starColor,
                modifier = Modifier
                    .semantics { drawableId = resId }
                    .size(48.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                if (offset.x > size.width / 2) {
                                    onChange(i.toFloat())
                                } else {
                                    onChange(i - .5f)
                                }
                            },
                        )
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRatingBar() {
    BookAppTheme() {
        Column {
            RatingBar(rating = 0f, onChange = {})
            RatingBar(rating = 0.5f, onChange = {})
            RatingBar(rating = 1f, onChange = {})
            RatingBar(rating = 1.5f, onChange = {})
            RatingBar(rating = 2f, onChange = {})
            RatingBar(rating = 2.5f, onChange = {})
            RatingBar(rating = 3f, onChange = {})
            RatingBar(rating = 3.5f, onChange = {})
            RatingBar(rating = 4f, onChange = {})
            RatingBar(rating = 4.5f, onChange = {})
            RatingBar(rating = 5f, onChange = {})
        }
    }
}