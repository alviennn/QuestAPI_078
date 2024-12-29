package com.example.remotedatabase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.remotedatabase.ui.view.DestinasiDetail
import com.example.remotedatabase.ui.view.DestinasiEntry
import com.example.remotedatabase.ui.view.DestinasiHome
import com.example.remotedatabase.ui.view.DetailScreen
import com.example.remotedatabase.ui.view.EntryMhsScreen
import com.example.remotedatabase.ui.view.HomeScreen
import com.example.remotedatabase.ui.view.mahasiswa.DestinasiUpdate
import com.example.remotedatabase.ui.view.mahasiswa.UpdateMhsScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route)},
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                    println("PengelolaHalaman: nim $nim")
                }
            )
        }
        composable(DestinasiEntry.route){
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM){
                    type = NavType.StringType
                }
            )
        ){
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let {nim ->
                DetailScreen(
                    nim = nim,
                    onBackClick = {
                        navController.navigate(DestinasiHome.route)
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$nim")
                    },
                )
            }
        }
        composable(
            "${DestinasiUpdate.route}/{nim}",
            arguments = listOf(
                navArgument("nim") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim")
            nim?.let { nim ->
                UpdateMhsScreen(
                    onBack = {
                        navController.navigate(DestinasiHome.route) },
                    onNavigate = { navController.navigate(DestinasiHome.route) },
                )
            }
        }
    }
}