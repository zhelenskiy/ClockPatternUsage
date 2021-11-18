import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.hours

class EventsStatisticImpl(private val clock: Clock): EventsStatistic {
    private val events: MutableMap<String, ArrayDeque<Instant>> = mutableMapOf()
    private val period = 1.hours

    private fun removeOld(name: String?, now: Instant = clock.now()) {
        if (name == null) {
            events.keys.forEach { removeOld(it, now) }
            return
        }
        val deque = events[name] ?: return
        while(deque.firstOrNull()?.takeIf { it + period < now } != null) {
            deque.removeFirst()
        }
        if (deque.isEmpty()) {
            events.remove(name)
        }
    }

    @Synchronized
    override fun incEvent(name: String) {
        val deque = events[name]?.apply { removeOld(name) } ?: ArrayDeque<Instant>().apply { events[name] = this }
        deque.addLast(clock.now())
    }

    @Synchronized
    override fun getEventsStatisticByName(name: String): Int {
        val deque = events[name] ?: return 0
        removeOld(name)
        return deque.size
    }

    @Synchronized
    override fun getAllEventStatistic(): Map<String, Int> {
        removeOld(null)
        return events.mapValues { (_, v) -> v.size }
    }

    override fun toString(): String =
        getAllEventStatistic().entries.sortedBy { it.key }.joinToString("\n") { (k, v) -> "$k: $v" }
}