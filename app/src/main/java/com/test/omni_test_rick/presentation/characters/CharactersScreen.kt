package com.test.omni_test_rick.presentation.characters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.test.omni_test_rick.common.CommonTexts
import com.test.omni_test_rick.common.SnackbarPosition
import com.test.omni_test_rick.common.SnackbarType
import com.test.omni_test_rick.domain.dto.Character
import com.test.omni_test_rick.presentation.components.MortyAppBar
import com.test.omni_test_rick.presentation.components.MortySnackBarHost
import com.test.omni_test_rick.ui.theme.AccentColor
import com.test.omni_test_rick.ui.theme.PrimaryColor
import com.test.omni_test_rick.utils.getString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharactersScreen(
    state: CharactersViewModel.CharactersState, viewModel: CharactersViewModel = hiltViewModel()
) {
    val commonTexts = CommonTexts()
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

    LaunchedEffect(viewModel.state) {
        viewModel.state.collectLatest { state ->
            errorFlow.value = state.charactersError.isNotEmpty()
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
                message = state.charactersError, actionLabel = commonTexts.commonUnderstoodLabel
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
        MortyAppBar(isMainScreen = true, context = context) {

        }
    }, snackbarHost = {
        MortySnackBarHost(
            snackbarHostState = snackbarHostState,
            snackbarType = snackbarType,
            snackbarPosition = snackbarPosition
        )
    }) { innerPadding ->
        val page = viewModel.page.value

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
                Column {
                    LazyColumn(Modifier.weight(1f)) {
                        items(state.characters) { character ->
                            if (character != null) {
                                CharacterItem(
                                    character = character,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clickable {
                                            viewModel.onCharacterClicked(character)
                                        },
                                    commonTexts,
                                    context
                                )
                            }
                        }
                    }
                    Pager(page, viewModel, state)
                }
            }
        }
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharacterItem(
    character: Character, modifier: Modifier = Modifier, commonTexts: CommonTexts, context: Context
) {
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = AccentColor,
        )
    ) {
        Row(
            modifier = modifier, verticalAlignment = Alignment.CenterVertically
        ) {

            GlideImage(
                model = character.image,
                contentDescription = getString(commonTexts.characterImage, context)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = character.name, color = Color.White, fontSize = 30.sp)
                Text(text = character.status, color = Color.White, fontSize = 20.sp)
                Text(text = character.species, color = Color.White, fontSize = 20.sp)
            }

        }
    }
}

@Composable
private fun Pager(
    page: Int, viewModel: CharactersViewModel, state: CharactersViewModel.CharactersState
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            onClick = {
                if (page > 1) {
                    viewModel.setPage(true)
                    viewModel.updateCharacters()
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            ), enabled = true
        ) {
            Text(text = "<")
        }

        Text(
            text = "$page de ${state.pages}"
        )

        Button(
            onClick = {
                if (page >= 1 && page < (state.pages!!)) {
                    viewModel.setPage(false)
                    viewModel.updateCharacters()
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            ), enabled = true
        ) {
            Text(text = ">")
        }
    }
}
