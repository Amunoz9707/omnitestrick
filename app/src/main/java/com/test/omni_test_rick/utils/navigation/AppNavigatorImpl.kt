package com.test.omni_test_rick.utils.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor() : AppNavigator {
    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override fun tryNavigateBack(route: String?, inclusive: Boolean) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route, inclusive = inclusive
            )
        )
    }

    override fun tryNavigateTo(
        route: String, popUpToRoute: String?, inclusive: Boolean, isSingleTop: Boolean
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }
}