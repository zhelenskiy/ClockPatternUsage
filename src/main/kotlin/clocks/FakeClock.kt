package clocks

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class FakeClock(var now: Instant = Instant.fromEpochSeconds(10_000_000)): Clock {
    override fun now(): Instant = now
}