package com.test.omni_test_rick.presentation.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.omni_test_rick.common.CommonKeys
import com.test.omni_test_rick.presentation.characters.CharactersScreen
import com.test.omni_test_rick.presentation.characters.CharactersViewModel
import com.test.omni_test_rick.presentation.detail.DetailedCharacterScreen
import com.test.omni_test_rick.presentation.detail.DetailedCharacterViewModel
import com.test.omni_test_rick.utils.navigation.Destination
import com.test.omni_test_rick.utils.navigation.NavHost
import com.test.omni_test_rick.utils.navigation.NavigationIntent

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val characterViewModel = hiltViewModel<CharactersViewModel>()
    val characterState by characterViewModel.state.collectAsState()
    val detailedCharacterViewModel = hiltViewModel<DetailedCharacterViewModel>()
    val detailedState by detailedCharacterViewModel.state.collectAsState()
    val navController = rememberNavController()
    val commonKeys = CommonKeys()

    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel, navHostController = navController
    )
    NavHost(
        navController = navController, startDestination = Destination.CharactersScreen
    ) {
        composable(route = Destination.CharactersScreen.fullRoute) {
            CharactersScreen(state = characterState, viewModel = characterViewModel)
        }

        composable(
            route = Destination.DetailedCharacterScreen.fullRoute + "/{${commonKeys.characterIDKey}}",
            arguments = listOf(navArgument(commonKeys.characterIDKey) { type = NavType.StringType })
        ) { backStackEntry ->
            DetailedCharacterScreen(
                state = detailedState,
                viewModel = detailedCharacterViewModel,
                characterID = backStackEntry.arguments?.getString(commonKeys.characterIDKey) as String
            )
        }

    }
}


@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>, navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}