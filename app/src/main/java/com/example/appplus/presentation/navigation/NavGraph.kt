package com.example.appplus.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.appplus.presentation.MainViewModel
import com.example.appplus.presentation.ui.CatalogScreen
import com.example.appplus.presentation.ui.DetailsScreen

@Composable
fun NavGraph(
    viewModel: MainViewModel,
    padding: PaddingValues
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Catalog
    ) {
        composable<Catalog> { CatalogScreen(viewModel, navController, padding) }
        composable<Details> {
            val args = it.toRoute<Details>()
            DetailsScreen(
                image = args.image,
                text = args.text,
                id = args.id,
                padding = padding
            )
        }
    }
}
