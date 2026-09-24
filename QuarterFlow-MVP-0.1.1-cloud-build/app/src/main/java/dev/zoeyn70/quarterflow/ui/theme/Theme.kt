package dev.zoeyn70.quarterflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val QuarterFlowColors = lightColorScheme(
    primary = Color(0xFF5E5A88),
    onPrimary = Color.White,
    secondary = Color(0xFF956783),
    tertiary = Color(0xFF557A78),
    background = Color(0xFFF8F5FB),
    surface = Color(0xFFFFFBFF),
    surfaceVariant = Color(0xFFEDE7F2),
    onSurface = Color(0xFF25232A)
)

@Composable
fun QuarterFlowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = QuarterFlowColors,
        content = content
    )
}
