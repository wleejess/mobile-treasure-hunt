package com.example.treasurehunt

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.treasurehunt.ui.ClueScreen
import com.example.treasurehunt.ui.CompletedScreen
import com.example.treasurehunt.ui.HomeScreen
import com.example.treasurehunt.ui.SolvedScreen
import com.example.treasurehunt.ui.TreasureHuntViewModel
import com.google.android.gms.location.FusedLocationProviderClient

/**
 * enum values that represent the screens in the app
 */
enum class TreasureHuntScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Clue(title = R.string.clue_page),
    Solved(title = R.string.clue_solved_page),
    Completed(title = R.string.completed_page)
}

data class TreasureHuntUiState(
    val isLoading: Boolean = false,
    val data: Any? = null,
    val errorMessage: String? = null
)

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasureHuntAppBar(
    currentScreen: TreasureHuntScreen,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
    )
}

@Composable
fun TreasureHuntApp(
    viewModel: TreasureHuntViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    fusedLocationClient: FusedLocationProviderClient // Add this parameter
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TreasureHuntScreen.valueOf(
        backStackEntry?.destination?.route ?: TreasureHuntScreen.Home.name
    )

    Scaffold(
        topBar = {
            TreasureHuntAppBar(
                currentScreen = currentScreen,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TreasureHuntScreen.Home.name,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable(route = TreasureHuntScreen.Home.name) {
                HomeScreen(navController, viewModel)
            }

            composable(route = TreasureHuntScreen.Clue.name) {
                ClueScreen(navController, viewModel, fusedLocationClient)
            }
            composable(route = TreasureHuntScreen.Solved.name) {
                SolvedScreen(navController, viewModel)
            }
            composable(route = TreasureHuntScreen.Completed.name) {
                CompletedScreen(navController, viewModel)
            }
        }
    }
}