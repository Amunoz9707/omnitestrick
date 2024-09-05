package com.test.omni_test_rick.presentation.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.test.omni_test_rick.common.CommonTexts
import com.test.omni_test_rick.common.DetailCharacterTexts
import com.test.omni_test_rick.common.SnackbarPosition
import com.test.omni_test_rick.common.SnackbarType
import com.test.omni_test_rick.presentation.components.MortyAppBar
import com.test.omni_test_rick.presentation.components.MortySnackBarHost
import com.test.omni_test_rick.ui.theme.PrimaryDarkColor
import com.test.omni_test_rick.utils.getString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun DetailedCharacterScreen(
    state: DetailedCharacterViewModel.DetailedCharacterState,
    viewModel: DetailedCharacterViewModel = hiltViewModel(),
    characterID: String
) {
    val activity = (LocalContext.current as? Activity)
    val context = activity!!.applicationContext
    val snackbarHostState = remember { SnackbarHostState() }
    val errorFlow = remember { MutableStateFlow(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarType by remember {
        mutableStateOf(SnackbarType.Default)
    }
    var snackbarPosition by remember {
        mutableStateOf(SnackbarPosition.Bottom)
    }
    val commonTexts = CommonTexts()
    val detailCharacterTexts = DetailCharacterTexts()

    LaunchedEffect(characterID) {
        viewModel.getCharacter(id = characterID)
    }

    LaunchedEffect(viewModel.state) {
        viewModel.state.collectLatest { state ->
            errorFlow.value = state.detailedCharacterError.isNotEmpty()
        }
    }

    LaunchedEffect(errorFlow) {
        errorFlow.collect { error ->
            snackbarType = if (error) SnackbarType.Error else SnackbarType.Success
            snackbarPosition = if (error) SnackbarPosition.Top else SnackbarPosition.Bottom
            showSnackbar = error
        }
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            val actionResult = snackbarHostState.showSnackbar(
                message = state.detailedCharacterError,
                actionLabel = commonTexts.commonUnderstoodLabel
            )

            showSnackbar = when (actionResult) {
                SnackbarResult.ActionPerformed -> {
                    true
                }

                SnackbarResult.Dismissed -> {
                    false
                }
            }
        }
    }


    Scaffold(topBar = {
        MortyAppBar(isMainScreen = false, barColor = PrimaryDarkColor, context) {
            viewModel.goBack()
        }
    }, snackbarHost = {
        MortySnackBarHost(
            snackbarHostState = snackbarHostState,
            snackbarType = snackbarType,
            snackbarPosition = snackbarPosition
        )
    }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FeatureTitle(
                        featureName = state.detailedCharacter?.name ?: "",
                        color = Color.DarkGray,
                    )
                    GlideImage(
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                            .shadow(
                                elevation = 10.dp, shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(10.dp)),
                        model = state.detailedCharacter?.image,
                        contentDescription = getString(commonTexts.characterImage, context)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    CharacterDetailsCard(state = state, detailCharacterTexts, context)
                }
            }
        }
    }
}

@Composable
fun CharacterDetailsCard(
    state: DetailedCharacterViewModel.DetailedCharacterState,
    detailCharacterTexts: DetailCharacterTexts,
    context: Context
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(), colors = CardDefaults.cardColors(
            containerColor = PrimaryDarkColor,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.TopCenter
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FeatureTitle(
                    featureName = getString(
                        detailCharacterTexts.characterStatusTitle, context
                    )
                )
                FeatureBody(featureDetail = state.detailedCharacter?.status ?: "")
                HorizontalDivider()
                FeatureTitle(
                    featureName = getString(
                        detailCharacterTexts.characterSpeciesTitle, context
                    )
                )
                FeatureBody(featureDetail = state.detailedCharacter?.species ?: "")
                HorizontalDivider()
                FeatureTitle(
                    featureName = getString(
                        detailCharacterTexts.characterOriginTitle, context
                    )
                )
                FeatureBody(featureDetail = state.detailedCharacter?.origin ?: "")
                HorizontalDivider()
                FeatureTitle(
                    featureName = getString(
                        detailCharacterTexts.characterLocationTitle, context
                    )
                )
                FeatureBody(featureDetail = state.detailedCharacter?.location ?: "")
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun FeatureTitle(featureName: String, color: Color = Color.White, fontSize: TextUnit = 30.sp) {
    Text(
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        text = featureName,
        color = color,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun FeatureBody(featureDetail: String) {
    Text(
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        text = featureDetail,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    )
}



