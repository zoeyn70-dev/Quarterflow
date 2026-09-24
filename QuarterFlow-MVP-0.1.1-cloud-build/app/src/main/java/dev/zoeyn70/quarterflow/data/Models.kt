package dev.zoeyn70.quarterflow.data

enum class ItemKind {
    ROUTINE,
    WALK,
    TRANSIT,
    ARRIVAL,
    CLASS,
    STUDY,
    FOOD,
    PERSONAL,
    RETURN_TRIP
}

data class ScheduleItem(
    val id: String,
    val start: String,
    val end: String? = null,
    val title: String,
    val detail: String = "",
    val location: String? = null,
    val kind: ItemKind,
    val important: Boolean = false
)

data class DayPlan(
    val dayName: String,
    val dateLabel: String,
    val items: List<ScheduleItem>
)

data class ValidationIssue(
    val message: String,
    val severity: Severity
)

enum class Severity { INFO, WARNING, ERROR }
