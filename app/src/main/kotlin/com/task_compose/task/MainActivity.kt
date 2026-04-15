package com.task_compose.task

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.task_compose.navigation.RootNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpdateSystemBars(isSystemInDarkTheme())
            val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            val darkTheme = isSystemInDarkTheme()
            val colors = when {
                dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
                dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
                darkTheme -> darkColorScheme()
                else -> lightColorScheme()
            }
            MaterialTheme(
                colorScheme = colors
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { paddingValues ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        RootNavHost()
                    }
                }
            }
        }
    }

    @Composable
    fun UpdateSystemBars(
        darkMode: Boolean,
    ) {
        val activity = (LocalContext.current as ComponentActivity)
        DisposableEffect(darkMode) {
            val barStyle = when (darkMode) {
                true -> SystemBarStyle.dark(Color.Transparent.toArgb())
                false -> SystemBarStyle.light(
                    Color.Transparent.toArgb(),
                    Color.Transparent.toArgb()
                )
            }
            activity.enableEdgeToEdge(
                statusBarStyle = barStyle,
                navigationBarStyle = barStyle
            )
            onDispose { }
        }
    }
}
