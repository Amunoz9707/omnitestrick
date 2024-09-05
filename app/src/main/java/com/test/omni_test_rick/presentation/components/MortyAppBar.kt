package com.test.omni_test_rick.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.omni_test_rick.R
import com.test.omni_test_rick.common.CommonTexts
import com.test.omni_test_rick.ui.theme.PrimaryColor
import com.test.omni_test_rick.utils.getString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MortyAppBar(
    isMainScreen: Boolean, barColor: Color = PrimaryColor, context: Context, onGoBack: () -> Unit
) {
    val commonTexts = CommonTexts()
    TopAppBar(title = {
        Text(
            text = getString(commonTexts.appNameBar, context),
            fontSize = 20.sp,
            color = Color.White
        )
    },
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = barColor),
        navigationIcon = {
            if (!isMainScreen) {
                IconButton(onClick = { onGoBack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_back),
                        tint = Color.White,
                        contentDescription = "back",
                        modifier = Modifier.size(31.dp)
                    )
                }
            }
        })
}