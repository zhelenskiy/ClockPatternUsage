package clocks

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class NormalClock: Clock {
    override fun now(): Instant = Clock.System.now()
}