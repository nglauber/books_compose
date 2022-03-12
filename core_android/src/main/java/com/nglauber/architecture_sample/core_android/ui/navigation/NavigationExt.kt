package com.nglauber.architecture_sample.core_android.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

private const val transitionDuration = 400
private const val transitionVerticalDuration = 500

@ExperimentalAnimationApi
val horizontalSlideInEnterTransition =
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(transitionDuration)
    )

@ExperimentalAnimationApi
val horizontalSlideOutExitTransition =
    slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(transitionDuration)
    )

@ExperimentalAnimationApi
val horizontalSlideInPopEnterTransition =
    slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(transitionDuration)
    )

@ExperimentalAnimationApi
val horizontalSlideOutPopExitTransition =
    slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(transitionDuration)
    )

@ExperimentalAnimationApi
val verticalSlideInEnterTransition =
    slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(transitionVerticalDuration)
    )

@ExperimentalAnimationApi
val verticalSlideOutPopExitTransition =
    slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(transitionVerticalDuration)
    )

@ExperimentalAnimationApi
fun NavGraphBuilder.composableScreen(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    targetIsPopup: (String?) -> Boolean = { false },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    this.composable(
        route,
        arguments,
        deepLinks,
        enterTransition = { horizontalSlideInEnterTransition },
        exitTransition = {
            if (targetIsPopup(targetState.destination.route))
                null
            else
                horizontalSlideOutExitTransition
        },
        popEnterTransition = {
            if (targetIsPopup(initialState.destination.route))
                null
            else
                horizontalSlideInPopEnterTransition
        },
        popExitTransition = { horizontalSlideOutPopExitTransition },
        content = content
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composablePopup(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    this.composable(
        route,
        arguments,
        deepLinks,
        enterTransition = { verticalSlideInEnterTransition },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { verticalSlideOutPopExitTransition },
        content
    )
}