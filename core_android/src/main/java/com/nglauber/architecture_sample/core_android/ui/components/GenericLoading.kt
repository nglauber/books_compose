package com.nglauber.architecture_sample.core_android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nglauber.architecture_sample.core_android.R
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme

@Composable
fun GenericLoading(
    message: String? = null
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = message ?: stringResource(id = R.string.msg_loading))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGenericLoading() {
    BookAppTheme {
        GenericLoading()
    }
}