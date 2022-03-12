package com.nglauber.architecture_sample.settings.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nglauber.architecture_sample.core_android.ui.theme.custom.ThemeMode
import com.nglauber.architecture_sample.settings.R
import com.nglauber.architecture_sample.settings.viewmodel.SettingsViewModel
import com.nglauber.architecture_sample.core_android.R as CoreR

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackPressed: () -> Unit,
) {
    val themeMode by viewModel.currentTheme.collectAsState()
    SettingsScreenContent(
        currentTheme = themeMode,
        onThemeChange = viewModel::setTheme,
        onBackPressed = onBackPressed,
    )
}

@Composable
fun SettingsScreenContent(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = CoreR.drawable.ic_arrow_back),
                            contentDescription = stringResource(id = CoreR.string.cd_back)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = CoreR.string.menu_action_settings))
                }
            )
        }
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(text = stringResource(id = R.string.msg_theme))
            val options = listOf(
                stringResource(id = R.string.msg_theme_system),
                stringResource(id = R.string.msg_theme_light),
                stringResource(id = R.string.msg_theme_dark),
            )
            val themeModes = listOf(
                ThemeMode.MODE_SYSTEM,
                ThemeMode.MODE_LIGHT,
                ThemeMode.MODE_DARK,
            )
            options.forEachIndexed { index, s ->
                val theme = themeModes[index]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            onThemeChange(theme)
                        }
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = theme == currentTheme,
                        onClick = { onThemeChange(theme) }
                    )
                    Text(text = s, Modifier.padding(8.dp))
                }
            }
        }
    }
}