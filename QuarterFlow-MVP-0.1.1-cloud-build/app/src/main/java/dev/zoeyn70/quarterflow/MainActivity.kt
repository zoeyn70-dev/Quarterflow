package dev.zoeyn70.quarterflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.zoeyn70.quarterflow.ui.QuarterFlowApp
import dev.zoeyn70.quarterflow.ui.theme.QuarterFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuarterFlowTheme {
                QuarterFlowApp()
            }
        }
    }
}
