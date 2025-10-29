package com.wtc.crm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.wtc.crm.ui.navigation.WTCApp
import com.wtc.crm.ui.theme.WTCTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity - Ponto de entrada da aplicação WTC CRM
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WTCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WTCApp()
                }
            }
        }
    }
}

