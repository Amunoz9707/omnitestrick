package com.test.omni_test_rick.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.test.omni_test_rick.common.SnackbarPosition
import com.test.omni_test_rick.common.SnackbarType

@Composable
fun MortySnackBarHost(
    snackbarHostState: SnackbarHostState,
    snackbarType: SnackbarType,
    snackbarPosition: SnackbarPosition
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .padding(top = if (snackbarType == SnackbarType.Error) 80.dp else 0.dp)
                .align(if (snackbarPosition == SnackbarPosition.Top) Alignment.TopCenter else Alignment.BottomCenter)
        ) { snackbarData ->
            MortySnackbar(snackbarData = snackbarData, snackbarType = snackbarType)
        }
    }
}