package com.expense.manager.plan.smapleproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.expense.manager.plan.smapleproject.ui.theme.SmapleProjectTheme
import com.expense.manager.plan.smapleproject.presentation.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val languageChange = intent?.extras?.getBoolean("languageChange", false) ?: false

        enableEdgeToEdge()
        setContent {

            val navHostController = rememberNavController()

            SmapleProjectTheme {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavHost(navHostController, languageChange)
                    }
                }
            }
        }
    }
}