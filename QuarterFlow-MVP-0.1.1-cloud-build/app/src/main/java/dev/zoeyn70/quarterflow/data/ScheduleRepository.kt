package dev.zoeyn70.quarterflow.data

import java.time.DayOfWeek
import java.time.LocalDate

object ScheduleRepository {
    private fun morningRoutine(leaveForStop: String): List<ScheduleItem> = listOf(
        ScheduleItem("ready", "6:40 AM", "7:05 AM", "Get ready", "Morning routine first.", kind = ItemKind.ROUTINE),
        ScheduleItem("dog", "7:05 AM", "7:25 AM", "Dog walk", "Protected 20-minute walk after getting ready.", kind = ItemKind.PERSONAL, important = true),
        ScheduleItem("breakfast", "7:25 AM", leaveForStop, "Breakfast + final prep", "Eat, fill water, check bag, charger, calculator and student ID.", kind = ItemKind.FOOD)
    )

    private val monWed = morningRoutine("7:40 AM") + listOf(
        ScheduleItem("walk-stop-mw", "7:40 AM", "7:50 AM", "Walk to Route 14", "Head to SE Madison & 11th, Stop 3637.", "SE Madison & 11th", ItemKind.WALK, true),
        ScheduleItem("route14-mw", "7:50 AM", "8:00 AM", "TriMet Route 14 — westbound", "SE Madison & 11th (Stop 3637) → SW Madison & 4th (Stop 3639).", "Route 14", ItemKind.TRANSIT, true),
        ScheduleItem("walk-eng", "8:00 AM", "8:15 AM", "Walk to Engineering Building", "Keep the final walking leg separate from transit.", "Engineering Building", ItemKind.WALK, true),
        ScheduleItem("arrival-eng", "8:15 AM", "9:00 AM", "Early-arrival buffer", "Arrive at the actual class building at least 30 minutes early during the first week.", "Engineering Building", ItemKind.ARRIVAL, true),
        ScheduleItem("calc", "9:00 AM", "10:40 AM", "Calculus I", "Class.", "Engineering Building", ItemKind.CLASS, true),
        ScheduleItem("transition", "10:40 AM", "11:00 AM", "Class transition", "Bathroom, water, quick reset.", "Engineering Building", ItemKind.ROUTINE),
        ScheduleItem("prog", "11:00 AM", "12:40 PM", "Intro to Programming", "Class.", "Engineering Building", ItemKind.CLASS, true),
        ScheduleItem("lunch", "12:40 PM", "1:20 PM", "Lunch", "Eat before the afternoon study block.", kind = ItemKind.FOOD),
        ScheduleItem("review", "1:20 PM", "2:30 PM", "Review + assignments", "Review lecture notes while they are fresh; add new due dates.", kind = ItemKind.STUDY),
        ScheduleItem("return15", "4:21 PM", "4:29 PM", "Route 15 home — eastbound", "From SW Yamhill & 6th to SE Belmont & 11th (Stop 400). Walk home after arrival. Verify the campus-to-stop connector before leaving.", "Route 15", ItemKind.RETURN_TRIP, true)
    )

    private val tueThu = morningRoutine("9:40 AM") + listOf(
        ScheduleItem("walk-stop-tt", "9:40 AM", "9:50 AM", "Walk to Route 14", "Head to SE Madison & 11th, Stop 3637.", "SE Madison & 11th", ItemKind.WALK, true),
        ScheduleItem("route14-tt", "9:50 AM", "10:00 AM", "TriMet Route 14 — westbound", "SE Madison & 11th (Stop 3637) → SW Madison & 4th (Stop 3639).", "Route 14", ItemKind.TRANSIT, true),
        ScheduleItem("walk-hoffman", "10:00 AM", "10:20 AM", "Walk to Hoffman Hall", "Final walking leg to the actual class building.", "Hoffman Hall", ItemKind.WALK, true),
        ScheduleItem("arrival-hoffman", "10:20 AM", "11:00 AM", "Early-arrival buffer", "At least 30 minutes early during the first week.", "Hoffman Hall", ItemKind.ARRIVAL, true),
        ScheduleItem("physics", "11:00 AM", "12:40 PM", "Physics with Calculus", "Class.", "Hoffman Hall", ItemKind.CLASS, true),
        ScheduleItem("lunch-tt", "12:40 PM", "1:20 PM", "Lunch", "Eat and reset.", kind = ItemKind.FOOD),
        ScheduleItem("physics-review", "1:20 PM", "2:30 PM", "Physics review", "Rewrite key equations, units and examples.", kind = ItemKind.STUDY),
        ScheduleItem("return15-tt", "Afternoon", null, "Route 15 home", "Standard return route. Departure is chosen from the current TriMet schedule based on actual campus departure time; do not guess a time.", "Route 15", ItemKind.RETURN_TRIP, true)
    )

    private val tuesdayLab = listOf(
        ScheduleItem("lab-prep", "4:45 PM", "5:20 PM", "Physics Lab prep", "Snack, bathroom, water, materials check and walk to the lab building.", "Hoffman Hall", ItemKind.ROUTINE, true),
        ScheduleItem("physics-lab", "5:30 PM", "8:30 PM", "Physics Lab", "Lab section.", "Hoffman Hall", ItemKind.CLASS, true),
        ScheduleItem("lab-return", "After 8:30 PM", null, "Return home", "Use the verified evening TriMet option for that date; no unverified time is hard-coded.", kind = ItemKind.RETURN_TRIP, important = true)
    )

    fun planFor(date: LocalDate): DayPlan {
        val items = when (date.dayOfWeek) {
            DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY -> monWed
            DayOfWeek.TUESDAY -> tueThu + tuesdayLab
            DayOfWeek.THURSDAY -> tueThu
            else -> listOf(
                ScheduleItem("weekend-ready", "Morning", null, "Get ready", kind = ItemKind.ROUTINE),
                ScheduleItem("weekend-dog", "After getting ready", null, "20-minute dog walk", "Keep the protected morning dog walk.", kind = ItemKind.PERSONAL, important = true),
                ScheduleItem("weekend-plan", "Late morning", null, "Weekly reset", "Check Canvas, upcoming assignments, transit changes and next school day.", kind = ItemKind.STUDY)
            )
        }
        return DayPlan(
            dayName = date.dayOfWeek.name.lowercase().replaceFirstChar { it.titlecase() },
            dateLabel = date.toString(),
            items = items
        )
    }

    fun quarterOverview(): List<String> = listOf(
        "Monday & Wednesday • Calculus I • 9:00–10:40 AM • Engineering Building",
        "Monday & Wednesday • Intro to Programming • 11:00 AM–12:40 PM • Engineering Building",
        "Tuesday & Thursday • Physics with Calculus • 11:00 AM–12:40 PM • Hoffman Hall",
        "Tuesday • Physics Lab • 5:30–8:30 PM • Hoffman Hall",
        "Morning order • Get ready → 20-minute dog walk → breakfast/final prep",
        "First-week rule • Arrive at the actual class building at least 30 minutes early",
        "Standard commute • Route 14 to school; Route 15 home"
    )
}
