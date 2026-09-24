package dev.zoeyn70.quarterflow.planner

import dev.zoeyn70.quarterflow.data.DayPlan
import dev.zoeyn70.quarterflow.data.ItemKind
import dev.zoeyn70.quarterflow.data.Severity
import dev.zoeyn70.quarterflow.data.ValidationIssue

object TaskOversightValidator {
    fun validate(plan: DayPlan): List<ValidationIssue> {
        val issues = mutableListOf<ValidationIssue>()
        val items = plan.items

        val ready = items.indexOfFirst { it.title == "Get ready" }
        val dog = items.indexOfFirst { it.title.contains("Dog walk", ignoreCase = true) }
        if (ready >= 0 && dog >= 0 && dog < ready) {
            issues += ValidationIssue("Dog walk must occur after getting ready.", Severity.ERROR)
        }

        val hasClass = items.any { it.kind == ItemKind.CLASS }
        if (hasClass && items.none { it.kind == ItemKind.TRANSIT }) {
            issues += ValidationIssue("School day is missing outbound transit.", Severity.ERROR)
        }
        if (hasClass && items.none { it.kind == ItemKind.WALK }) {
            issues += ValidationIssue("Transit plan must keep walking legs explicit.", Severity.ERROR)
        }
        if (hasClass && items.none { it.kind == ItemKind.ARRIVAL }) {
            issues += ValidationIssue("First-week school day is missing the building-arrival buffer.", Severity.ERROR)
        }
        if (hasClass && items.none { it.kind == ItemKind.RETURN_TRIP }) {
            issues += ValidationIssue("School day is missing a return-home plan.", Severity.ERROR)
        }

        return issues
    }

    fun wallpaperHeaderIsValid(header: String): Boolean =
        !header.contains("tomorrow", ignoreCase = true)
}
