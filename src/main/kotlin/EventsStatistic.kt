interface EventsStatistic {
    fun incEvent(name: String)
    fun getEventsStatisticByName(name: String): Double
    fun getAllEventStatistic(): Map<String, Double>
}