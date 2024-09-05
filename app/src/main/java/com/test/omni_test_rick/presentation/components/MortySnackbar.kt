package com.test.omni_test_rick.presentation.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.test.omni_test_rick.common.SnackbarType
import com.test.omni_test_rick.ui.theme.ErrorColor
import com.test.omni_test_rick.ui.theme.SuccessColor

@Composable
fun MortySnackbar(snackbarData: SnackbarData, snackbarType: SnackbarType) {
    Snackbar(
        snackbarData = snackbarData,
        containerColor = if (snackbarType == SnackbarType.Error) ErrorColor else SuccessColor,
        contentColor = Color.White,
        actionColor = Color.White
    )
}