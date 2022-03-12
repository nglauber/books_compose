package com.nglauber.architecture_sample.core.navigation

abstract class AppRoutes(val baseRoute: String) {
    open val route: String = baseRoute
}