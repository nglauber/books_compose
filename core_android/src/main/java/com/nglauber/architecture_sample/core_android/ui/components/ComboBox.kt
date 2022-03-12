package com.nglauber.architecture_sample.core_android.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTheme
import kotlinx.coroutines.launch

@Composable
fun <T> ComboBox(
    items: List<T>,
    label: String,
    value: T? = null,
    onItemSelected: (T) -> Unit,
    itemAsString: (T) -> String = { it.toString() }
) {
    var text by remember { mutableStateOf(value?.let { itemAsString(it) } ?: "") }
    var isOpened by remember { mutableStateOf(false) }

    fun rotationValue(open: Boolean) = if (open) 180f else 0f
    val animRotation = remember {
        Animatable(initialValue = rotationValue(isOpened))
    }
    val coroutineScope = rememberCoroutineScope()
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        coroutineScope.launch {
            animRotation.animateTo(
                targetValue = rotationValue(isOpened), animationSpec = tween(
                    durationMillis = 200, easing = FastOutLinearInEasing
                )
            )
        }
        isOpened = it
    }
    val userSelectedItem: (T) -> Unit = {
        text = itemAsString(it)
        onItemSelected(it)
    }
    Box {
        val componentColor = AppTheme.colors.primary
        Column {
            OutlinedTextField(value = text,
                onValueChange = { text = it },
                label = {
                    Text(text = label)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = if (isOpened) componentColor else AppTheme.colors.onSurface.copy(
                        alpha = ContentAlpha.disabled
                    )
                ),
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .rotate(animRotation.value)
                    )
                })
            DropDownList(
                list = items,
                onItemSelected = userSelectedItem,
                onDismissRequest = openCloseOfDropDownList,
                itemAsString = itemAsString,
                expanded = isOpened,
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(Color.Transparent)
                .clickable(onClick = {
                    openCloseOfDropDownList(true)
                })
        )
    }
}

@Composable
private fun <T> DropDownList(
    list: List<T>,
    onItemSelected: (T) -> Unit,
    onDismissRequest: (Boolean) -> Unit,
    itemAsString: (T) -> String,
    expanded: Boolean = false,
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onDismissRequest = { onDismissRequest(false) },
    ) {
        list.forEach {
            DropdownMenuItem(modifier = Modifier.fillMaxWidth(), onClick = {
                onDismissRequest(false)
                onItemSelected(it)
            }) {
                Text(
                    itemAsString(it), modifier = Modifier.wrapContentWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewComboBox() {
    BookAppTheme {
        ComboBox(
            items = listOf("Option 1", "Option 2", "Option 3"),
            label = "Options",
            onItemSelected = {},
            value = "Option 1"
        )
    }
}