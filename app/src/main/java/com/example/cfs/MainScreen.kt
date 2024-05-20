import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cfs.R
import com.example.cfs.ui.ListScreen
import com.example.cfs.ui.ListViewModel
import com.example.cfs.ui.RequestScreen
import com.example.cfs.ui.items


enum class Routes() {
    Request,
    List,
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onExitClick: () -> Unit) {
    val navController = rememberNavController()
    val listViewModel = ListViewModel()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any() { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (currentDestination?.hierarchy?.any() { it.route == item.route } == true) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        }
                    )
                }
            }
        },
        topBar = {
            CFSTopAppBar(onNavBtnClck = onExitClick)
        }
    ) { paddingValues ->
        NavHost(

            navController = navController,
            startDestination = Routes.Request.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Routes.Request.name) {
                RequestScreen()
                BackHandler(true) { // NOW WE LOG OUT WHEN WE PRESS BACK
                    onExitClick()
                    Log.i("LOG_TAG", "Clicked back")
                }
            }
            composable(route = Routes.List.name) {
                listViewModel.fetchData()
                ListScreen(viewModel = listViewModel)
                BackHandler(true) {
                    onExitClick()
                    Log.i("LOG_TAG", "Clicked back")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CFSTopAppBar(
    modifier: Modifier = Modifier,
    onNavBtnClck: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary, // Use primary color from the theme
            titleContentColor = MaterialTheme.colorScheme.onPrimary, // Use onPrimary color for title text
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = onNavBtnClck) {
                Icon(
                    Icons.Filled.ExitToApp,
                    "contentDescription",
                    tint = MaterialTheme.colorScheme.onPrimary
                )

            }
        }
    )
}







