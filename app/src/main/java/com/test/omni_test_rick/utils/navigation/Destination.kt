package com.test.omni_test_rick.utils.navigation

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object CharactersScreen : NoArgumentsDestination(CHARACTER_ROUTE)

    object DetailedCharacterScreen : NoArgumentsDestination(DETAILED_CHARACTER_ROUTE)

    companion object {
        private const val CHARACTER_ROUTE = "character_route"
        private const val DETAILED_CHARACTER_ROUTE = "detailed_character_route"
    }
}