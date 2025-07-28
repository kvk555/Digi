package com.example.appplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appplus.presentation.MainViewModel
import com.example.appplus.presentation.navigation.NavGraph
import com.example.appplus.presentation.ui.AppBar
import com.example.appplus.presentation.viewModelFactory
import com.example.appplus.ui.theme.AppPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPlusTheme {
                val viewModel = viewModel<MainViewModel>(
                    factory = viewModelFactory {
                        MainViewModel(MyApp.appModule.repository)
                    }
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppBar() }) { padding ->
                    NavGraph(viewModel, padding)
                }
            }
        }
    }
}
