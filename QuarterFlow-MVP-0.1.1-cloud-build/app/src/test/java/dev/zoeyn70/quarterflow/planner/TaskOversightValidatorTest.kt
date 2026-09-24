package dev.zoeyn70.quarterflow.planner

import dev.zoeyn70.quarterflow.data.ScheduleRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class TaskOversightValidatorTest {
    @Test
    fun thursdayPhysicsPlanHasNoValidatorErrors() {
        val thursday = LocalDate.of(2026, 10, 1)
        val issues = TaskOversightValidator.validate(ScheduleRepository.planFor(thursday))
        assertTrue(issues.isEmpty())
    }

    @Test
    fun wallpaperHeaderRejectsTomorrow() {
        assertFalse(TaskOversightValidator.wallpaperHeaderIsValid("Tomorrow — Thursday"))
        assertTrue(TaskOversightValidator.wallpaperHeaderIsValid("Thursday • October 1"))
    }
}
