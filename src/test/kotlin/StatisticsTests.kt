import clocks.FakeClock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class StatisticsTests {
    @Test
    fun incOnly() {
        val clock = FakeClock()
        val statistic = EventsStatisticImpl(clock)
        assertEquals(emptyMap(), statistic.getAllEventStatistic())
        repeat(2) {
            statistic.incEvent("kotlin")
            assertEquals(mapOf("kotlin" to (it + 1) / 60.0), statistic.getAllEventStatistic())
            assertEquals((it + 1) / 60.0, statistic.getEventsStatisticByName("kotlin"))
        }
        statistic.incEvent("haskell")
        assertEquals(0.0, statistic.getEventsStatisticByName("java"))
        assertEquals(1 / 60.0, statistic.getEventsStatisticByName("haskell"))
        assertEquals("haskell: ${1 / 60.0}rpm\nkotlin: ${2 / 60.0}rpm", statistic.toString())
    }

    @Test
    fun withErasure() {
        val clock = FakeClock()
        val statistic = EventsStatisticImpl(clock)
        statistic.incEvent("kotlin")
        clock.now += 59.minutes
        assertEquals(1 / 60.0, statistic.getEventsStatisticByName("kotlin"))
        clock.now += 1.minutes
        assertEquals(1 / 60.0, statistic.getEventsStatisticByName("kotlin"))
        statistic.incEvent("kotlin")
        assertEquals(2 / 60.0, statistic.getEventsStatisticByName("kotlin"))
        clock.now += 1.seconds
        assertEquals(1 / 60.0, statistic.getEventsStatisticByName("kotlin"))
        clock.now += 2.hours
        assertEquals(0 / 60.0, statistic.getEventsStatisticByName("kotlin"))
    }
}