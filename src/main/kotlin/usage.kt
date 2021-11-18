import clocks.NormalClock

fun main() {
    val stats: EventsStatistic = EventsStatisticImpl(NormalClock())
    println(stats)
    repeat(2) {
        stats.incEvent("kotlin")
        println(stats)
    }
    stats.incEvent("haskell")
    println(stats.getEventsStatisticByName("java"))
    println(stats.getEventsStatisticByName("haskell"))
    println(stats)
}