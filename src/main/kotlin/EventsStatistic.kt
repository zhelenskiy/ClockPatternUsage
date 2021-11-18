interface EventsStatistic {
    fun incEvent(name: String)
    fun getEventsStatisticByName(name: String): Int
    fun getAllEventStatistic(): Map<String, Int>
}