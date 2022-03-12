package com.nglauber.architecture_sample.core_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nglauber.architecture_sample.core.ErrorEntity
import com.nglauber.architecture_sample.core_android.R
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTheme

@Composable
fun GenericError(
    error: ErrorEntity? = null,
    dismissText: String? = null,
    onDismissAction: (() -> Unit)? = null,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
            tint = AppTheme.colors.error,
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = error?.message ?: stringResource(id = R.string.msg_generic_error))
        onDismissAction?.let {
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = onDismissAction) {
                Text(text = dismissText ?: stringResource(id = R.string.button_ok))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGenericError() {
    BookAppTheme {
        GenericError(onDismissAction = {})
    }
}