package com.test.omni_test_rick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.test.omni_test_rick.presentation.main.MainScreen
import com.test.omni_test_rick.ui.theme.OmniTestRickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmniTestRickTheme {
                MainScreen()
            }
        }
    }
}

