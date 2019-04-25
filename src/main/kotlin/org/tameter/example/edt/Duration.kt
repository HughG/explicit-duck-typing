package org.tameter.example.edt

interface Duration {
    val asSeconds: Long
    val asMinutes: Long
    val asHours: Long
}

const val SECONDS_PER_MINUTE: Long = 60
const val MINUTES_PER_HOUR: Long = 60

inline class Seconds(override val asSeconds: Long) : Duration {
    override val asMinutes: Long get() = asSeconds / SECONDS_PER_MINUTE
    override val asHours: Long get() = asMinutes / (SECONDS_PER_MINUTE * MINUTES_PER_HOUR)
}

inline val Long.seconds get() = Seconds(this)
inline val Int.seconds get() = this.toLong().seconds
val MO = 2.seconds