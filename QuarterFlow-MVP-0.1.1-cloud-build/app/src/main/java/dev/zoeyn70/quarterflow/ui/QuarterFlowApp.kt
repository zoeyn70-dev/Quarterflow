package dev.zoeyn70.quarterflow.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.zoeyn70.quarterflow.data.DayPlan
import dev.zoeyn70.quarterflow.data.ScheduleItem
import dev.zoeyn70.quarterflow.data.ScheduleRepository
import dev.zoeyn70.quarterflow.planner.TaskOversightValidator
import java.time.LocalDate

private enum class Tab(val label: String, val glyph: String) {
    TODAY("Today", "●"),
    NEXT("Next", "→"),
    QUARTER("Quarter", "▦"),
    WALLPAPER("Wallpaper", "▣"),
    SETTINGS("Settings", "⚙")
}

@Composable
fun QuarterFlowApp() {
    var selected by remember { mutableStateOf(Tab.TODAY) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selected == tab,
                        onClick = { selected = tab },
                        icon = { Text(tab.glyph) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { padding ->
        when (selected) {
            Tab.TODAY -> DayScreen(LocalDate.now(), "Today", padding)
            Tab.NEXT -> DayScreen(LocalDate.now().plusDays(1), "Next day", padding)
            Tab.QUARTER -> QuarterScreen(padding)
            Tab.WALLPAPER -> WallpaperScreen(padding)
            Tab.SETTINGS -> SettingsScreen(padding)
        }
    }
}

@Composable
private fun DayScreen(date: LocalDate, heading: String, outerPadding: PaddingValues) {
    val plan = ScheduleRepository.planFor(date)
    val issues = TaskOversightValidator.validate(plan)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(heading, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("${plan.dayName} • ${plan.dateLabel}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                if (issues.isEmpty()) "Task Oversight: plan passes current validation rules."
                else "Task Oversight: ${issues.size} item(s) need attention.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        items(plan.items) { item -> ScheduleCard(item) }
        if (issues.isNotEmpty()) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Validator", fontWeight = FontWeight.Bold)
                        issues.forEach { Text("• ${it.message}") }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleCard(item: ScheduleItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (item.important) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(item.title, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(if (item.end != null) "${item.start}–${item.end}" else item.start)
            }
            if (item.location != null) Text(item.location, color = MaterialTheme.colorScheme.primary)
            if (item.detail.isNotBlank()) Text(item.detail)
        }
    }
}

@Composable
private fun QuarterScreen(outerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(outerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Text("Fall 2026 Quarter", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) }
        items(ScheduleRepository.quarterOverview()) { line ->
            Card(Modifier.fillMaxWidth()) { Text(line, Modifier.padding(14.dp)) }
        }
    }
}

@Composable
private fun WallpaperScreen(outerPadding: PaddingValues) {
    val rules = listOf(
        "Large clear clock/widget-safe area at the top.",
        "Central schedule card stays readable and detailed.",
        "Art, Portland motifs and affirmations stay farther inward from crop-prone edges.",
        "Schedule fidelity comes before decoration.",
        "Include transit legs/stops/times when verified, early-arrival buffer, study purpose and important deadlines.",
        "Never use the word “Tomorrow” in the wallpaper header; use the actual day/date.",
        "Pastel, feminine, professional styling; subtle/moderate/high visual variation can rotate without changing the core layout."
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(outerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Text("Wallpaper spec", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) }
        items(rules) { rule -> Card(Modifier.fillMaxWidth()) { Text("• $rule", Modifier.padding(14.dp)) } }
        item {
            Text(
                "MVP 0.1.0 stores and validates the wallpaper rules; automatic image generation and automatic wallpaper setting are not enabled yet.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SettingsScreen(outerPadding: PaddingValues) {
    val settings = listOf(
        "Morning plan delivery target: 7:00 AM full master schedule",
        "Evening preview target: 8:00 PM next-day preview + wallpaper",
        "Outbound school route: TriMet Route 14",
        "Standard return route: TriMet Route 15",
        "First-week building-arrival buffer: at least 30 minutes",
        "Transit rule: use uploaded/current TriMet schedules; do not invent unverified times"
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(outerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Text("Settings", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) }
        items(settings) { setting -> Card(Modifier.fillMaxWidth()) { Text(setting, Modifier.padding(14.dp)) } }
    }
}
